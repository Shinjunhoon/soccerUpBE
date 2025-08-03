package com.example.careercubebackend.api.JoinRequest.dto;

import com.example.careercubebackend.api.JoinRequest.domain.enums.Position;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.AverageAge;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.SkillLevel;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.TeamType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class JoinRequestDto {
    private Long teamId;
    private Position position;
    private Long userId;
    @NotBlank(message = "지역은 필수입니다.")
    private String region; // DTO에서는 문자열로 받아서 서비스에서 Enum으로 변환
    private TeamType teamType;
    private SkillLevel skillLevel;
    private String teamIntro;

}
