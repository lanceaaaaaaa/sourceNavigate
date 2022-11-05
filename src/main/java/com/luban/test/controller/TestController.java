package com.luban.test.controller;

import com.luban.common.EmailUtil;
import com.luban.test.dao.TbBrand;
import com.luban.test.dao.TbBrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Autowired
    private EmailUtil emailUtil;

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        // 测试文本邮件发送（无附件）
        String to = "qq15999953604@163.com"; // 这是个假邮箱，写成你自己的邮箱地址就可以
        String title = "文本邮件发送测试";
        String content = "文本邮件发送测试";
        emailUtil.sendMessage(to, title, content);
        return "hello";
    }

    @RequestMapping("testMabatis")
    @ResponseBody
    public List<TbBrand> testMabatis(){
       return tbBrandMapper.selectByExample(null);
    }

    @RequestMapping(value = "toEdit")
    public ModelAndView toEdit(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("edit.html");
        return  mv;
    }

    @RequestMapping(value = "toIndex")
    public ModelAndView toIndex(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index.html");
        return  mv;
    }



}
