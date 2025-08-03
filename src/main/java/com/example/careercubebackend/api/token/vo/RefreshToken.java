package com.example.careercubebackend.api.token.vo;


import com.example.careercubebackend.api.token.exception.RefreshTokenException;
import com.example.careercubebackend.api.token.exception.RefreshTokenExceptionResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    protected static final Map<String, Long> refreshTokens = new HashMap<>();


    public static Long getRefreshToken(final String refreshToken) {
        return Optional.ofNullable(refreshTokens.get(refreshToken))
                .orElseThrow(() -> new RefreshTokenException(RefreshTokenExceptionResult.NOT_EXIST));
    }


    public static void putRefreshToken(final String refreshToken, Long id) {
        refreshTokens.put(refreshToken, id);
    }


    private static void removeRefreshToken(final String refreshToken) {
        refreshTokens.remove(refreshToken);
    }

    // user refresh token remove
    public static void removeUserRefreshToken(final long refreshToken) {
        for(Map.Entry<String, Long> entry : refreshTokens.entrySet()) {
            if(entry.getValue() == refreshToken) {
                removeRefreshToken(entry.getKey());
            }
        }
    }

}
