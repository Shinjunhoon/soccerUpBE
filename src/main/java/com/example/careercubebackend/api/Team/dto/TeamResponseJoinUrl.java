package com.example.careercubebackend.api.Team.dto;


import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamResponseJoinUrl {
    private Long id;
    private String name;
    private String inviteLink;
    private LocalDateTime createdAt;

    public TeamResponseJoinUrl(Team team) {
        this.id = team.getId();
        this.name = team.getTeamName();
        this.inviteLink = "https://soccer-up-puwg.vercel.app/invite/" + team.getInviteCode();
        this.createdAt = team.getCreatedAt();
    }

}