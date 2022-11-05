package com.luban.user.controller;

import com.luban.common.EmailUtil;
import com.luban.common.ResultModel;
import com.luban.note.model.NoteModel;
import com.luban.user.config.VerifyCodeFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    private EmailUtil emailUtil;
    @RequestMapping(value = "login")
    public ModelAndView toLogin(Model model){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login.html");
        return  mv;
    }

    @ResponseBody
    @RequestMapping(value = "getVerifyCode")
    public ResultModel getVerifyCode(){
        try {
            String to = "qq15999953604@163.com"; // 这是个假邮箱，写成你自己的邮箱地址就可以
            String title = "验证码发送";
            Random random = new Random();
            String verifyCode = Integer.toString(1000 + random.nextInt(9000));
            String content = "验证码为-"+verifyCode;
            VerifyCodeFilter.cacheCodeMap.put("cacheCode",verifyCode);
            emailUtil.sendMessage(to, title, content);
            return ResultModel.success("获取验证码成功");
        }catch(Exception ex){
            return ResultModel.error("获取验证码失败");
        }
    }
}
