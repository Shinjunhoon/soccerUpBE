package com.example.careercubebackend.api.user.dto.response;


import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.Region;
import com.example.careercubebackend.api.Team.dto.TeamResponseJoinUrl;
import com.example.careercubebackend.api.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Builder
public record UserGetResponseDTO(long id, String userId, String password, String name, String email, Integer age, Region region, List<TeamResponseJoinUrl> teamResponseJoinUrlList) {


}
