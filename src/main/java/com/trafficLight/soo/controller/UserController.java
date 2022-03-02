package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.User;
import com.trafficLight.soo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 마이페이지
     * @param model (MyPageForm)
     * @return users/myPage
     */
    @GetMapping("/users/myPage")
    public String myPage(Model model, @AuthenticationPrincipal User authUser){
        Optional<User> optionalUser = userService.getUser(authUser.getUserId());
        User user = optionalUser.get();
        MyPageForm myPageForm = MyPageForm.builder()
                .userId(user.getUserId())
                //security getUsername override 때문에, username은 getUserName() 으로 가져옴
                .username(user.getUserName())
                .email(user.getEmail())
                .posts(user.getPosts())
                .comments(user.getComments())
                .build();
        model.addAttribute("myPageForm", myPageForm);
        return "users/myPage";
    }

    /**
     * 회원정보 수정
     * @param myPageForm
     * @return /
     */
    @PostMapping("/users/myPage")
    public String editMyPage(@Valid MyPageForm myPageForm){
        System.out.println("---회원정보수정---");
        userService.changeInfo(myPageForm);
        return "redirect:/";
    }

    /**
     * 로그인 페이지
     * @param model (SignInForm)
     * @return users/signIn
     */
    @GetMapping("/users/signIn")
    public String signIn(Model model){
        System.out.println("로그인 페이지");
        model.addAttribute("signInForm", new SignInForm());
        return "users/signIn";
    }

    /**
     * 로그인 실패
     * @return users/signInFail
     */
    @GetMapping("/users/signInFail")
    public String signInFail(){
        return "users/signInFail";
    }

    /**
     * 회원가입 페이지
     * @param model (SignUpForm)
     * @return users/signUp
     */
    @GetMapping("/users/signUp")
    public String signUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        System.out.println("회원가입 페이지");
        return "users/signUp";
    }

    /**
     * 회원가입 - 저장
     * @param signUpForm
     * @param bindingResult
     * @return success - redirect:/
     *         fail - users/signUpFail
     */
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

    /**
     * 회원가입 실패
     * @return users/signUpFail
     */
    @GetMapping("/users/signUpFail")
    public String signUpFail(){
        return "users/signUpFail";
    }
}
