package com.example.careercubebackend.api.email.controller;

import com.example.careercubebackend.api.common.response.entity.ApiResponseEntity;
import com.example.careercubebackend.api.email.application.EmailService;
import com.example.careercubebackend.api.email.dto.EmailSendRequest;
import com.example.careercubebackend.api.email.dto.EmailVerifyRequest;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;


    @PostMapping("/send-code")
    public ResponseEntity<ApiResponseEntity> sendVerificationCode(@Valid @RequestBody EmailSendRequest request) {
        log.info("인증 코드 발송 요청: {}", request.getEmail());
        try {
            emailService.sendEmail(request.getEmail());
            // 성공 응답 (데이터 없이 메시지만)
            return ApiResponseEntity.successResponseEntity("인증 코드를 성공적으로 발송했습니다.");
        } catch (MessagingException e) {
            log.error("이메일 발송 실패 - 대상: {}, 오류: {}", request.getEmail(), e.getMessage());
            // 실패 응답 (ApiResponseEntity의 failResponseEntity는 HTTP Status 200 OK를 반환함)
            return ApiResponseEntity.failResponseEntity("이메일 발송에 실패했습니다. 다시 시도해주세요.");
        }
    }

      @PostMapping("/verify-code")
    public ResponseEntity<ApiResponseEntity> verifyCode(@Valid @RequestBody EmailVerifyRequest request) {
        log.info("인증 코드 검증 요청 - 이메일: {}, 코드: {}", request.getEmail(), request.getCode());
        Boolean isVerified = emailService.verifyEmailCode(request.getEmail(), request.getCode());

        if (isVerified) {
            // 인증 성공 응답 (boolean 데이터 포함)
            return ApiResponseEntity.successResponseEntity("이메일 인증에 성공했습니다.", true);
        } else {
            // 인증 실패 응답 (boolean 데이터 포함)
            return ApiResponseEntity.failResponseEntity(false, "인증 코드가 일치하지 않거나 만료되었습니다.");
        }
    }
}