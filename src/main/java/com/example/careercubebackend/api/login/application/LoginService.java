package com.example.careercubebackend.api.login.application;


import com.example.careercubebackend.api.login.dto.request.LoginRequestDTO;
import com.example.careercubebackend.api.login.dto.response.LoginResponseDTO;

public interface LoginService {

    LoginResponseDTO login(final LoginRequestDTO loginRequestDTO);

}
