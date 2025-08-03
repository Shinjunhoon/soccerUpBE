package com.example.careercubebackend.api.user.controller;

import com.example.careercubebackend.api.common.response.entity.ApiResponseEntity;
import com.example.careercubebackend.api.user.application.UserAddService;
import com.example.careercubebackend.api.user.dto.request.CheckUserIdDto;
import com.example.careercubebackend.api.user.dto.request.UserAddRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final UserAddService userAddService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseEntity> register(@RequestBody @Valid UserAddRequestDTO userAddRequestDTO) {
        // 사용자 정보 저장
        userAddService.addUser(userAddRequestDTO);

        return ApiResponseEntity.successResponseEntity();
    }


    @PostMapping("/checkUser")
    public ResponseEntity<ApiResponseEntity> checkUser(@RequestBody @Valid CheckUserIdDto checkUserIdDto) {
        return ApiResponseEntity.checkResponseEntity(userAddService.checkUserId(checkUserIdDto));
    }
}
