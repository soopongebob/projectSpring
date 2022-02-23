package com.trafficLight.soo.controller;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class PostListForm {
    private Long postIdx;
    private String subject;
    private String writer;
    private Long viewCount;
    private LocalDateTime createdTime;

    @QueryProjection
    public PostListForm(Long postIdx, String subject, String writer, Long viewCount, LocalDateTime createdTime) {
        this.postIdx = postIdx;
        this.subject = subject;
        this.writer = writer;
        this.viewCount = viewCount;
        this.createdTime = createdTime;
    }
}
