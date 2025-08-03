package com.example.careercubebackend.api.user.dto.request;


import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.Region;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserAddRequestDTO {

    @NotNull
    @NotEmpty
    private String userId;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String name;


    @NotNull
    @NotEmpty
    private String email;



    private Integer age;


    private Region region;
}
