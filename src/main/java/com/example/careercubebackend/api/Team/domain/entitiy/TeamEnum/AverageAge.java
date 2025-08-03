package com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum;// src/main/java/com/yourpackage/domain/enums/AverageAge.java


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AverageAge {
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES_PLUS("50대 이상"),
    MIXED("혼합");

    private final String description; // 한글 설명
}