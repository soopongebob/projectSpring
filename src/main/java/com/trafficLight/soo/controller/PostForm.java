package com.trafficLight.soo.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Data
@Getter @Setter
public class PostForm {

    @NotEmpty
    private String subject;
    @NotEmpty
    private String content;
}
