package com.example.careercubebackend.api.TeamMember.dto;

import com.example.careercubebackend.api.JoinRequest.domain.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberResponseDto {
    Long id;
    String username;
    Position position;
}
