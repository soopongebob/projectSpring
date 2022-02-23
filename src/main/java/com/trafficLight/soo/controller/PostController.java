package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.Post;
import com.trafficLight.soo.entity.User;
import com.trafficLight.soo.service.PostService;
import com.trafficLight.soo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    /**
     * 게시판 리스트
     * @param model
     * @return
     */
    @GetMapping("/post/list")
    public String postList(Model model,
                           @PageableDefault(page = 0, size = 5, sort="postIdx", direction = Sort.Direction.DESC)
                           Pageable pageable){
        //페이징
        Page<PostListForm> posts = postService.findAll(pageable);
        model.addAttribute("posts", posts);
        return "post/list";
    }

    @GetMapping("/post/write")
    public String postWriteForm(Model model){
        model.addAttribute("postWriteForm", new PostWriteForm());
        return "post/write";
    }

    @PostMapping("/post/write")
    public String postWrite(@Valid PostWriteForm postWriteForm, @AuthenticationPrincipal User user){
        System.out.println("글 저장 -----");
        Optional<User> writer = userService.getUser(user.getUserId());
        System.out.println("writer : " + writer.get().getUserId());
        Post post = Post.builder()
                .subject(postWriteForm.getSubject())
                .content(postWriteForm.getContent())
                .viewCount(0L)
                .user(writer.get())
                .comments(null)
                .build();
        postService.registration(post);

        return "redirect:/post/list";
    }
}
