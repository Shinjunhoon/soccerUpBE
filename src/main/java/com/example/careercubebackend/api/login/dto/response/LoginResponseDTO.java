package com.example.careercubebackend.api.login.dto.response;

import lombok.Builder;

@Builder
public record LoginResponseDTO(String accessToken, String refreshToken, String userName) {
}
