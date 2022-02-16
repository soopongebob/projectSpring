package com.trafficLight.soo.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class loginForm {

    @NotEmpty(message = "아이디는 필수입니다.")
    private String userId;
}
