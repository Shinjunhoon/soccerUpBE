package com.example.careercubebackend.api.token.application;


import com.example.careercubebackend.api.token.dto.response.RefreshTokenResponseDTO;

public interface RefreshTokenService {


    RefreshTokenResponseDTO refreshToken(final String refreshToken);

}
