package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.Post;
import com.trafficLight.soo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    /**
     * 메인페이지
     * @param model : posts
     * @param pageable
     * @return index
     */
    @RequestMapping("/")
    public String home(Model model,
                       @PageableDefault(page = 0, size = 5, sort="postIdx", direction = Sort.Direction.DESC)
                       Pageable pageable){
        Page<PostListForm> posts = postService.findAll(pageable);
        model.addAttribute("posts", posts);
        return "index";
    }

}
