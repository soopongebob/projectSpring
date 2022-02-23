package com.trafficLight.soo.controller;

import com.querydsl.core.annotations.QueryProjection;
import com.trafficLight.soo.entity.Comment;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class PostViewForm {

    private Long postIdx;
    private String subject;
    private String content;
    private String writer;
    private String viewCount;
    private List<Comment> comments;

    public PostViewForm() {
    }

    @QueryProjection
    public PostViewForm(Long postIdx, String subject, String content, String writer, String viewCount, List<Comment> comments) {
        this.postIdx = postIdx;
        this.subject = subject;
        this.content = content;
        this.writer = writer;
        this.viewCount = viewCount;
        this.comments = comments;
    }
}
