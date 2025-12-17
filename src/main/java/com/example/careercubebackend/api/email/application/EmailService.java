package com.example.careercubebackend.api.email.application;


import ch.qos.logback.core.Context;
import com.example.careercubebackend.config.Redis.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor

public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    private final RedisUtil redisUtil;
    @Value("${spring.mail.properties.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Value("${spring.mail.password}")
    private String mailPassword; // 앱 비밀번호

    private String createCode() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i > 65) && (i <= 90 | i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public void sendEmail(String toEmail) throws MessagingException {

        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
            log.info("기존 인증 코드 삭제: {}", toEmail);
        }

        String authCode = createCode();


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("[NEXUS] 이메일 본인 인증 코드입니다.");
        helper.setFrom(senderEmail);

        String emailContent = String.format(
                "안녕하세요.\n\n" +
                        "NEXUS 회원가입을 위한 이메일 본인 인증 코드입니다.\n" +
                        "아래 코드를 회원가입 페이지에 입력해주세요.\n\n" +
                        "인증 코드: **%s**\n\n" +
                        "이 코드는 %d분 후에 만료됩니다.\n" +
                        "본인이 요청하지 않은 경우, 이 이메일을 무시하셔도 됩니다.\n\n" +
                        "감사합니다,\nNEXUS 팀",
                authCode,
                authCodeExpirationMillis / (1000 * 60)
        );
        helper.setText(emailContent, false);


        redisUtil.setDataExpire(toEmail, authCode, authCodeExpirationMillis / 1000L);



        try {
            mailSender.send(message);
            log.info("인증 코드를 {}로 성공적으로 전송했습니다. 코드: {}", toEmail, authCode);
        } catch (MailException e) {
            log.error("이메일 전송 중 오류 발생 ({}): {}", toEmail, e.getMessage());
            throw new MessagingException("이메일 전송에 실패했습니다. 다시 시도해주세요.", e);
        }
    }


    public Boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        log.debug("이메일 '{}'에 대해 Redis에서 찾은 코드: {}", email, codeFoundByEmail);

        if (codeFoundByEmail == null) {
            log.warn("이메일 '{}'에 대한 유효한 인증 코드를 찾을 수 없거나 만료되었습니다.", email);
            return false;
        }

        boolean isVerified = codeFoundByEmail.equals(code);

        if (isVerified) {
            redisUtil.deleteData(email);
            log.info("이메일 '{}' 인증 성공. Redis 코드 삭제.", email);
        } else {
            log.warn("이메일 '{}' 인증 실패. 입력 코드: '{}', 저장된 코드: '{}'", email, code, codeFoundByEmail);
        }
        return isVerified;
    }
}

