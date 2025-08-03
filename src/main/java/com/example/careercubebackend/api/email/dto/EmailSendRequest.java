package com.example.careercubebackend.api.email.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EmailSendRequest {
    @NotBlank(message = "이메일은 필수 입력 값입니다.") // 빈 문자열, null, 공백만 있는 문자열 허용 안함
    @Email(message = "유효한 이메일 형식이 아닙니다.") // 이메일 형식 검증
    private String email;
}