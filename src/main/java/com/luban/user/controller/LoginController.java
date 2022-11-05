package com.luban.user.controller;

import com.luban.note.model.NoteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping(value = "login")
    public ModelAndView toLogin(Model model){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login.html");
        return  mv;
    }
}
