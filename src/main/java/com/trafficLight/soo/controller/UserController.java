package com.trafficLight.soo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    //마이페이지
    @GetMapping("/users/myPage")
    public String myPage(){
        return "myPage";
    }

    //로그인 페이지
    @GetMapping("/users/signIn")
    public String signIn(Model model){
        model.addAttribute("loginForm", new loginForm());
        return "users/signIn";
    }

    //회원가입 페이지
    @GetMapping("/users/signUp")
    public String signUp(){
        return "users/signUp";
    }
}
