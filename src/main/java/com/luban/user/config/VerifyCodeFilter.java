package com.luban.user.config;

import com.luban.common.VerifyCodeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VerifyCodeFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(VerifyCodeFilter.class);

    public static final Map<String, String> cacheCodeMap = new ConcurrentHashMap<>();

    @Autowired
    private MyAuthenticationFailureHandler loginFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals("/login")&&request.getMethod().equalsIgnoreCase("post")){
            try {
                validate(request);
            } catch (VerifyCodeException e) {
                loginFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        // 3. 校验通过，就放行
        filterChain.doFilter(request, response);
    }
    /* 验证保存在session的验证码和表单提交的验证码是否一致 */
    private void validate(HttpServletRequest request) throws ServletRequestBindingException {
        String verifyCode = ServletRequestUtils.getStringParameter(request, "verifyCode");
        String cacheCode = cacheCodeMap.get("cacheCode");
        log.info("获取提交的verifyCode",verifyCode);
        log.info("获取保存的cacheCode",cacheCode);
        if (StringUtils.isBlank(verifyCode) || StringUtils.isBlank(cacheCode)){
            throw new VerifyCodeException("请获取验证码！");
        }

        if(!cacheCode.equalsIgnoreCase(verifyCode)){
            throw new VerifyCodeException("验证码不正确！");
        }
        cacheCodeMap.remove("cacheCode");
    }
}
