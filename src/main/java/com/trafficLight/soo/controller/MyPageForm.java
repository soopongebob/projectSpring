package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.Comment;
import com.trafficLight.soo.entity.Post;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class MyPageForm {

    @NotNull
    private String userId;
    @NotNull
    private String username;
    @NotNull
    private String email;
    
    private List<Post> posts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public MyPageForm(String userId, String username, String email, List<Post> posts, List<Comment> comments) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.posts = posts;
        this.comments = comments;
    }
}
