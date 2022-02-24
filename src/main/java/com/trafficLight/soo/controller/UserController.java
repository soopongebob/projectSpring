package com.trafficLight.soo.controller;

import com.trafficLight.soo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private HttpSession httpSession;
    private final PasswordEncoder passwordEncoder;

    //마이페이지
    @GetMapping("/users/myPage")
    public String myPage(){
        return "users/myPage";
    }

    //로그인 페이지
    @GetMapping("/users/signIn")
    public String signIn(Model model){
        System.out.println("로그인 페이지");
        model.addAttribute("signInForm", new SignInForm());
        return "users/signIn";
    }

    @GetMapping("/users/signInFail")
    public String signInFail(){
        return "users/signInFail";
    }

    //회원가입 페이지
    @GetMapping("/users/signUp")
    public String signUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        System.out.println("회원가입 페이지");
        return "users/signUp";
    }

    //회원가입 회원저장 redirect
    @PostMapping("/users/signUp")
    public String signUp(@Valid SignUpForm signUpForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("오류");
            return "redirect:/";
        }
        System.out.println("회원가입 저장");
        System.out.println("비밀번호 수정(encoding)");
        signUpForm.encodePassword(passwordEncoder);
        String check = userService.signUp(signUpForm);
        if(check != null){      //성공
            System.out.println("회원가입 성공");
            return "redirect:/";
        }else{
            return "users/signUpFail";
        }
    }

    @GetMapping("/users/signUpFail")
    public String signUpFail(){
        return "users/signUpFail";
    }

}
