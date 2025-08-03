package com.example.careercubebackend.api.TeamMember.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionResult {



    NOT_TEAM_OWNER(HttpStatus.FORBIDDEN, "해당 팀의 오너만이 이 작업을 수행할 수 있습니다."),
    INVALID_JOIN_REQUEST(HttpStatus.BAD_REQUEST, "해당 팀과 요청에 대한 정보가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}