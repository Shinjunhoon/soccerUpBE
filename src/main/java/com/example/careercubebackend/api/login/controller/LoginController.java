package com.example.careercubebackend.api.login.controller;


import com.example.careercubebackend.api.common.response.entity.ApiResponseEntity;
import com.example.careercubebackend.api.login.application.LoginService;
import com.example.careercubebackend.api.login.dto.request.LoginRequestDTO;
import com.example.careercubebackend.api.login.dto.response.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        // login 체크 후 token 생성
        LoginResponseDTO loginResponseDTO = loginService.login(loginRequestDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " +loginResponseDTO.accessToken() );
        headers.set("Refresh-Token", loginResponseDTO.refreshToken()); // 필요하면 추가
        return ApiResponseEntity.successResponseEntity(loginResponseDTO);
    }

}
