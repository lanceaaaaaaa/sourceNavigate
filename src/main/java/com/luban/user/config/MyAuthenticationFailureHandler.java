package com.luban.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luban.common.VerifyCodeException;
import com.luban.user.dao.UserRegisterDao;
import com.luban.user.service.impl.UserDetailsServiceImpl;
import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.inmemory.request.InMemorySlidingWindowRequestRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private  static ObjectMapper objectMapper = new ObjectMapper();
    //引入MyUserDetailsServiceMapper


    public MyAuthenticationFailureHandler(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    //规则定义：1小时之内5次机会，第6次失败就触发限流行为（禁止访问）
    Set<RequestLimitRule> rules =
            Collections.singleton(RequestLimitRule.of(Duration.ofMinutes(60),5));
    RequestRateLimiter limiter = new InMemorySlidingWindowRequestRateLimiter(rules);


    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        //从request或request.getSession中获取登录用户名
        String username = request.getParameter("username");

        //默认提示信息
        String errorMsg;
        if(exception instanceof LockedException){ //账户被锁定了
            errorMsg = "您已经多次登陆失败，账户已被锁定，请稍后再试！";
        }else if(exception instanceof SessionAuthenticationException){
            errorMsg = exception.getMessage();
        }else if(exception instanceof VerifyCodeException){
            errorMsg = exception.getMessage();
        }else{
            errorMsg = "请检查您的用户名和密码输入是否正确";
        }

        //每次登陆失败计数器加1，并判断该用户是否已经到了触发了锁定规则
        boolean reachLimit = limiter.overLimitWhenIncremented(username);
        if(reachLimit){ //如果触发了锁定规则，修改数据库 accountNonLocked字段锁定用户
            userDetailsServiceImpl.updateLockedByUserId(username);
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

            errorMsg = "您多次登陆失败，账户已被锁定，请稍后再试！";
        }

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorMsg));

    }
}
