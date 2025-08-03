package com.example.careercubebackend.api.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

// 이메일 인증 코드 검증 요청 DTO
@Getter
@Setter
public  class EmailVerifyRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "인증 코드는 필수 입력 값입니다.")
    private String code;
}
