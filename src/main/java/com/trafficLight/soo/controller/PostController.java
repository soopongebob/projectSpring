package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.Post;
import com.trafficLight.soo.entity.User;
import com.trafficLight.soo.service.PostService;
import com.trafficLight.soo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    /**
     * 게시판 메인
     * @param model
     * @return
     */
    @GetMapping("/post/list")
    public String postList(Model model){
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "post/list";
    }

    @GetMapping("/post/write")
    public String postWriteForm(Model model){
        model.addAttribute("postForm", new PostForm());
        return "post/write";
    }

    @PostMapping("/post/write")
    public String postWrite(@Valid PostForm postForm, @AuthenticationPrincipal User user){
    //페이징 추가
        System.out.println("글 저장 -----");
        Optional<User> writer = userService.getUser(user.getUserId());
        System.out.println("writer : " + writer.get().getUserId());
        Post post = Post.builder()
                .subject(postForm.getSubject())
                .content(postForm.getContent())
                .viewCount(0L)
                .user(writer.get())
                .comments(null)
                .build();
        postService.regist(post);

        return "redirect:/post/list";
    }
}
