package com.trafficLight.soo.controller;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MyPageForm {

    @NotNull
    private String userId;
    @NotNull
    private String username;
    @NotNull
    private String email;
    
    //TODO:내가쓴 글, 내가쓴 댓글

    @Builder
    public MyPageForm(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
