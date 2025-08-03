package com.example.careercubebackend.api.JoinRequest.application.impl;

import com.example.careercubebackend.api.JoinRequest.domain.repository.RepositoryRepository;
import com.example.careercubebackend.api.JoinRequest.dto.JoinRequestDto;
import com.example.careercubebackend.api.JoinRequest.domain.entitiy.TeamJoinRequest;
import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.Region;
import com.example.careercubebackend.api.Team.domain.repository.TeamRepository;
import com.example.careercubebackend.api.user.domain.entity.User;
import com.example.careercubebackend.api.user.domain.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Service
public class JoinRequestService implements com.example.careercubebackend.api.JoinRequest.application.JoinRequestService {
  UserRepository userRepository;
  TeamRepository teamRepository;
  RepositoryRepository repositoryRepository;

    @Override
    public void teamJoinRequest(JoinRequestDto joinRequestDto, Long requesterId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new IllegalArgumentException("요청자를 찾을 수 없습니다."));
        Team team = teamRepository.findById(joinRequestDto.getTeamId())
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

        // ⭐ DTO의 region(String)을 엔티티의 province(Region Enum)와 cityCode(String)로 파싱
        String regionString = joinRequestDto.getRegion();
        Region provinceEnum = null;
        String cityCodeString = null;

        if (regionString != null && !regionString.isEmpty()) {
            String[] parts = regionString.split("-");
            try {
                // 첫 번째 부분은 시/도 (Region Enum)
                provinceEnum = Region.valueOf(parts[0]);
                // 두 번째 부분이 있다면 시/군/구 (String)
                if (parts.length > 1) {
                    cityCodeString = parts[1];
                }
            } catch (IllegalArgumentException e) {
                // Enum 값에 없는 잘못된 지역 코드가 넘어왔을 경우 처리
                throw new IllegalArgumentException("유효하지 않은 지역 코드입니다: " + parts[0], e);
            }
        } else {
            // region이 필수라고 DTO에 정의되어 있지만, 만약의 경우를 대비하여
            throw new IllegalArgumentException("지역 정보는 필수입니다.");
        }

        TeamJoinRequest teamJoinRequest = new TeamJoinRequest();

        teamJoinRequest.setRequester(requester);
        teamJoinRequest.setTeam(team);
        teamJoinRequest.setPosition(joinRequestDto.getPosition());


        teamJoinRequest.setProvince(provinceEnum);

        teamJoinRequest.setCityCode(cityCodeString);


        teamJoinRequest.setSkillLevel(joinRequestDto.getSkillLevel());


        teamJoinRequest.setTeamIntro(joinRequestDto.getTeamIntro());



        repositoryRepository.save(teamJoinRequest);
    }
}
