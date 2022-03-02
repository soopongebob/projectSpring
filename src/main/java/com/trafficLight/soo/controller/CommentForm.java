package com.trafficLight.soo.controller;

import com.trafficLight.soo.entity.User;
import lombok.Data;

@Data
public class CommentForm {
    private Long postIdx;
    private User user;
    private String content;
}
