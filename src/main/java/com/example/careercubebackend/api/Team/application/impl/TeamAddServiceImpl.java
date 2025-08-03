package com.example.careercubebackend.api.Team.application.impl;

import com.example.careercubebackend.api.JoinRequest.domain.enums.Position;
import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.Region;
import com.example.careercubebackend.api.Team.domain.repository.TeamRepository;
import com.example.careercubebackend.api.Team.dto.TeamRequestDto;
import com.example.careercubebackend.api.Team.dto.TeamResponseDto;
import com.example.careercubebackend.api.Team.dto.TeamResponseJoinUrl;
import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import com.example.careercubebackend.api.TeamMember.domain.repository.TeamMemberRepository;
import com.example.careercubebackend.api.user.domain.entity.User;
import com.example.careercubebackend.api.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class TeamAddServiceImpl implements com.example.careercubebackend.api.Team.application.TeamAddServiceImpl {

    TeamRepository teamRepository;
    UserRepository userRepository;
    TeamMemberRepository teamMemberRepository;

    @Override
    @Transactional
    public TeamResponseJoinUrl createTeam(Long userId, TeamRequestDto teamRequestDto) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        log.info("팀 생성 요청 수신. 팀 이름: {}", teamRequestDto.getTeamName());
        log.info("요청 데이터: {}", teamRequestDto);


        Region province = null;
        String cityCode = null;

        if (teamRequestDto.getRegion() != null && !teamRequestDto.getRegion().isEmpty()) {
            String[] regionParts = teamRequestDto.getRegion().split("-");
            try {

                province = Region.fromProvinceCode(regionParts[0]);


                if (regionParts.length > 1) {
                    cityCode = regionParts[1];
                }
            } catch (IllegalArgumentException e) {

                log.error("유효하지 않은 지역 코드입니다: {}", teamRequestDto.getRegion(), e);
                throw new IllegalArgumentException("유효하지 않은 시/도 코드입니다: " + regionParts[0]);
            }
        } else {

            throw new IllegalArgumentException("지역 정보는 필수입니다.");
        }


        Team team = new Team();
        team.setTeamName(teamRequestDto.getTeamName());
        team.setOwner(user);
        team.setProvince(province);
        team.setCityCode(cityCode);
        team.setAverageAge(teamRequestDto.getAverageAge());
        team.setTeamType(teamRequestDto.getTeamType());
        team.setSkillLevel(teamRequestDto.getSkillLevel());
        team.setTeamIntro(teamRequestDto.getTeamIntro());


        user.getTeams().add(team);


        teamRepository.save(team);


        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(team);
        teamMember.setUser(user);

        teamMember.setPosition(Position.FW);

        teamMemberRepository.save(teamMember);

        log.info("팀 생성 완료, teamId: {}, inviteCode: {}", team.getId(), team.getInviteCode());


        return new TeamResponseJoinUrl(team);
    }
}