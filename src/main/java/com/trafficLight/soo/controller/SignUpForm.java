package com.trafficLight.soo.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SignUpForm {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    private String auth = "ROLE_USER";

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

}
