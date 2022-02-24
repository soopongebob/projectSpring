package com.trafficLight.soo.controller;

import lombok.Data;

@Data
public class PostEditForm {

    private Long postIdx;
    private String subject;
    private String content;

    public PostEditForm(Long postIdx, String subject, String content) {
        this.postIdx = postIdx;
        this.subject = subject;
        this.content = content;
    }
}
