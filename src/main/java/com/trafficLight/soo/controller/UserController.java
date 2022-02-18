package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.Auth;
import com.trafficLight.soo.entity.User;
import com.trafficLight.soo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private HttpSession httpSession;

    //마이페이지
    @GetMapping("/users/myPage")
    public String myPage(){
        return "myPage";
    }

    //로그인 페이지
    @GetMapping("/users/signIn")
    public String signIn(Model model){
        model.addAttribute("signInForm", new SignInForm());
        return "users/signIn";
    }

    //로그인 확인
    @PostMapping("/users/signIn")
    public String signIn(@Valid SignInForm signInForm, BindingResult bindingResult,
                         HttpServletRequest request){

        //오류확인
        if(bindingResult.hasErrors()){
            return "users/signIn";
        }

        String userId = userService.checkIdAndPwd(signInForm);
        if(userId != null){
            httpSession = request.getSession();
            httpSession.setAttribute("loginUser", userId);
            return "index";
        }else{
            bindingResult.reject("loginFail", "아이디 또는 비밀번호 오류입니다.");
            return "users/signInFail";
        }
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
            return "redirect:/";
        }

        String uuid = UUID.randomUUID().toString();
        Auth auth = Auth.USER;
        User user = User.builder()
                .uuid(uuid)
                .userId(signUpForm.getUserId())
                .username(signUpForm.getUserName())
                .password(signUpForm.getPassword())
                .email(signUpForm.getEmail())
                .auth(auth)
                .build();

        //return null일 경우, 가입 완료
        String check = userService.signUp(user);
        if(check == null){      //성공
            return "index";
        }else{
            return "users/signUpFail";
        }
    }

    @GetMapping("/users/signUpFail")
    public String signUpFail(){
        return "users/signUpFail";
    }

    @PostMapping("/users/logout")
    public String logout(HttpServletRequest request){
        httpSession = request.getSession(false);

        if(httpSession != null){
            httpSession.invalidate();
        }

        return "redirect:/";
    }
}
