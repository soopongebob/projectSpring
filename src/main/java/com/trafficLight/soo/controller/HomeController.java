package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "index";
    }
}
