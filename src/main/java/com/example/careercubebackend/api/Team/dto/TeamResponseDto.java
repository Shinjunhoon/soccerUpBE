package com.example.careercubebackend.api.Team.dto;


import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamResponseDto {
    private Long id;
    private String name;
    public TeamResponseDto(Team team) {
        this.id = team.getId();
        this.name = team.getTeamName();
    }


}
