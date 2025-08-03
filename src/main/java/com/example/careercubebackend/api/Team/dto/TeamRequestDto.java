package com.example.careercubebackend.api.Team.dto;


import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.AverageAge;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.SkillLevel;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.TeamType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class TeamRequestDto {
    @NotBlank(message = "팀 이름은 필수입니다.")
    private String teamName;

    @NotBlank(message = "지역은 필수입니다.")
    private String region;

    private AverageAge averageAge; // Enum 타입으로 직접 받을 수도 있음
    private TeamType teamType;
    private SkillLevel skillLevel;
    private String teamIntro;
}
