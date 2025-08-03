package com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum;// src/main/java/com/yourpackage/domain/enums/TeamType.java


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TeamType {
    CLUB("동호회"),
    SCHOOL("학교/학원"),
    COMPANY("회사"),
    FRIENDS("친구/지인"),
    OTHER("기타");

    private final String description; // 한글 설명
}