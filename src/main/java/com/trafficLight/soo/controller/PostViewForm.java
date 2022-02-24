package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.Comment;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
public class PostViewForm {

    private Long postIdx;
    private String subject;
    private String content;
    private String writer;
    private Long viewCount;
    private String createdDate;
    private List<Comment> comments;

    public PostViewForm(Long postIdx, String subject, String content, String writer, Long viewCount, String createdDate, List<Comment> comments) {
        this.postIdx = postIdx;
        this.subject = subject;
        this.content = content;
        this.writer = writer;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
        this.comments = comments;
    }
}
