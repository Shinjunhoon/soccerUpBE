package com.example.careercubebackend.api.TeamMember.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TeamMemberRequestDto {
    Long teamId;
    Long userId;
}
