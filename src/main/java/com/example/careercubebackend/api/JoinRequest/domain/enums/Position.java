package com.example.careercubebackend.api.JoinRequest.domain.enums;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal=true , level= AccessLevel.PRIVATE)
public enum Position {
    FW("공격수"),
    MF("미드필더"),
    DF("수비수"),
    GK("골키퍼");

    private final String Position;
}
