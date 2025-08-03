package com.example.careercubebackend.api.JoinRequest.domain.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal=true , level= AccessLevel.PRIVATE)
public enum RequestStatus {
    PENDING("대기"),
    APPROVED("수락"),
    REJECTED("거절");

    private String status;
}
