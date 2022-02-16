package com.trafficLight.soo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class SingUpDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Length(min = 1, max = 30, message = "아이디는 1자 이상, 30자 미만입니다.")
    private String userId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Length(min = 1, max = 30, message = "이름이 너무 짧거나 깁니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호은 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

}
