package com.example.careercubebackend.api.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleName {

    ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

    private final String role;


    public static RoleName fromRole(String role) {
        for(RoleName roleName : RoleName.values()) {
            if(roleName.getRole().equalsIgnoreCase(role)) {
                return roleName;
            }
        }

        throw new IllegalArgumentException("No Role Found!");
    }

}
