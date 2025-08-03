package com.example.careercubebackend.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CheckUserIdDto {

    @NotBlank(message = "아이디 입력은 필수입니다.")
    private String userId;
}
