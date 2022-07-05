package com.trafficLight.soo.controller;

import lombok.Data;

@Data
public class SearchCondition {
    //게시글 제목, 내용, 글쓴이로 검색
    private String subject;
    private String content;
    private String username;
}
