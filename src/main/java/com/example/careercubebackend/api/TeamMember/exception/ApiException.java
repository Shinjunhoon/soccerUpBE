package com.example.careercubebackend.api.TeamMember.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final ExceptionResult exceptionResult;

    public ApiException(ExceptionResult exceptionResult) {
        super(exceptionResult.getMessage());
        this.exceptionResult = exceptionResult;
    }
}