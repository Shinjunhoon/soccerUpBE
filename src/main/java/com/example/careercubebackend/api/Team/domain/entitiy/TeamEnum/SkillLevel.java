package com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum;// src/main/java/com/yourpackage/domain/enums/SkillLevel.java


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SkillLevel {
    BEGINNER("초급"),
    INTERMEDIATE("중급"),
    ADVANCED("고급");

    private final String description; // 한글 설명
}