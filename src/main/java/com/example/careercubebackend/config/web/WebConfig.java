package com.example.careercubebackend.config.web;


import com.example.careercubebackend.api.user.enums.converter.RoleNameConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new RoleNameConverter());
    }@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 요청 경로 허용
                .allowedOrigins("http://localhost:3000", "http://localhost:3000/","https://soccer-up-puwg.vercel.app/")  // 두 가지 모두 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // OPTIONS 요청도 허용
                .allowedHeaders("Authorization", "Content-Type") // Authorization 헤더와 Content-Type 허용
                .exposedHeaders("Authorization") // 클라이언트에서 확인할 수 있도록 노출
                .allowCredentials(true); // 쿠키, 인증정보 포함 허용

    }

}

