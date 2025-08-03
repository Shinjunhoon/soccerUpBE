package com.example.careercubebackend.api.Squad.application.impl;

import com.example.careercubebackend.api.Squad.domain.entity.Squad;
import com.example.careercubebackend.api.Squad.domain.repository.SquadRepository;
import com.example.careercubebackend.api.Squad.dto.SquadRequestDto;
import com.example.careercubebackend.api.Squad.dto.SquadResponse;
import com.example.careercubebackend.api.SquadPosition.SquadPositionRepository;
import com.example.careercubebackend.api.SquadPosition.domain.SquadPosition;
import com.example.careercubebackend.api.SquadPosition.dto.SquadPositionRequestDto;
import com.example.careercubebackend.api.SquadPosition.dto.SquadPositionResponse;
import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.Team.domain.repository.TeamRepository;
import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import com.example.careercubebackend.api.TeamMember.domain.repository.TeamMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j; // ⭐ @Slf4j 임포트 추가
import org.springframework.stereotype.Service;

// ⭐ 기존 SLF4J Logger 임포트 제거 (Lombok이 자동 생성)
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class SquadAddService implements com.example.careercubebackend.api.Squad.application.SquadAddService {



    TeamMemberRepository teamMemberRepository;
    TeamRepository teamRepository;
    SquadRepository squadRepository;
    SquadPositionRepository squadPositionRepository;

    @Transactional
    @Override
    public SquadResponse addSquad(SquadRequestDto squadRequestDto, Long userId) {
        Team team = teamRepository.findById(squadRequestDto.getTeamId())
                .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + squadRequestDto.getTeamId()));


        if (!teamMemberRepository.existsByUserIdAndTeamId(userId, team.getId())) {
            throw new SecurityException("User is not authorized to manage squads for this team.");
        }

        Squad squad = new Squad();
        squad.setTeam(team); // Squad에 Team 설정
        squad.setFormationType(squadRequestDto.getFormationType());
        squad.setIsDefault(true);


        for (SquadPositionRequestDto dto : squadRequestDto.getPositionRequestDtoList()) {
            TeamMember teamMember = null;
            String playerName = null;

            if (dto.getTeamMemberId() != null) {
                teamMember = teamMemberRepository.findById(dto.getTeamMemberId())
                        .orElseThrow(() -> new EntityNotFoundException("TeamMember not found with ID: " + dto.getTeamMemberId()));
                if (teamMember.getUser() != null && teamMember.getUser().getUserInfo() != null) {
                    playerName = teamMember.getUser().getUserInfo().getName();
                }
            }


            log.debug("--- DEBUG: addSquad method ---");
            log.debug("Squad object (before building SquadPosition): {}", squad);
            if (squad.getTeam() != null) {
                log.debug("Squad's Team object: {}", squad.getTeam());
                log.debug("Squad's Team ID: {}", squad.getTeam().getId());
            } else {
                log.debug("Squad's Team is NULL!");
            }
            log.debug("--- DEBUG End ---");


            SquadPosition squadPosition = SquadPosition.builder()
                    .fieldPositionCode(dto.getFieldPositionCode())
                    .teamMember(teamMember)
                    .username(playerName)
                    .squad(squad)
                    .team(squad.getTeam())
                    .build();

            squad.addSquadPosition(squadPosition);
        }
        Squad savedSquad = squadRepository.save(squad);
        return  convertToSquadResponse(savedSquad);
    }

    private SquadResponse convertToSquadResponse(Squad squad) {
        List<SquadPositionResponse> positionResponses = squad.getSquadPositions().stream()
                .map(pos -> {
                    String playerName = (pos.getTeamMember() != null) ? pos.getTeamMember().getUser().getUserInfo().getName() : null;
                    Long teamMemberId = (pos.getTeamMember() != null) ? pos.getTeamMember().getId() : null;

                    return SquadPositionResponse.builder()
                            .id(pos.getId())
                            .fieldPositionCode(pos.getFieldPositionCode())
                            .teamMemberId(teamMemberId)
                            .playerName(playerName)
                            .build();
                })
                .collect(Collectors.toList());

        return SquadResponse.builder()
                .id(squad.getId())
                .teamId(squad.getTeam().getId())
                .formationType(squad.getFormationType())
                .isDefault(squad.getIsDefault())
                .createdAt(squad.getCreatedAt())
                .updatedAt(squad.getUpdatedAt())
                .positions(positionResponses)
                .build();
    }

    @Transactional
    @Override
    public SquadResponse updateSquad(Long squadId, SquadRequestDto request, Long userId) {
        Squad squad = squadRepository.findById(squadId)
                .orElseThrow(() -> new EntityNotFoundException("Squad not found with ID: " + squadId));

        if (!teamMemberRepository.existsByUserIdAndTeamId(userId, squad.getTeam().getId())) {
            throw new SecurityException("User is not authorized to update this squad.");
        }

        squad.setFormationType(request.getFormationType());
        squad.getSquadPositions().clear();


        for (SquadPositionRequestDto dto : request.getPositionRequestDtoList()) {
            TeamMember teamMember = null;
            String playerName = null;

            if (dto.getTeamMemberId() != null) {
                teamMember = teamMemberRepository.findById(dto.getTeamMemberId())
                        .orElseThrow(() -> new EntityNotFoundException("TeamMember not found with ID: " + dto.getTeamMemberId()));
                if (teamMember.getUser() != null && teamMember.getUser().getUserInfo() != null) {
                    playerName = teamMember.getUser().getUserInfo().getName();
                }
            }

            // ⭐⭐⭐ @Slf4j 로거 사용 시작 ⭐⭐⭐
            log.debug("--- DEBUG: updateSquad method ---");
            log.debug("Squad object (before building SquadPosition): {}", squad);
            if (squad.getTeam() != null) {
                log.debug("Squad's Team object: {}", squad.getTeam());
                log.debug("Squad's Team ID: {}", squad.getTeam().getId());
            } else {
                log.debug("Squad's Team is NULL!");
            }
            log.debug("--- DEBUG End ---");
            // ⭐⭐⭐ @Slf4j 로거 사용 끝 ⭐⭐⭐

            SquadPosition squadPosition = SquadPosition.builder()
                    .fieldPositionCode(dto.getFieldPositionCode())
                    .teamMember(teamMember)
                    .username(playerName)
                    .squad(squad)
                    .team(squad.getTeam())
                    .build();

            squad.addSquadPosition(squadPosition);
        }

        Squad savedSquad = squadRepository.save(squad);
        return  convertToSquadResponse(savedSquad);
    }
}