package com.luban.test.controller;

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

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
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
