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

import java.time.LocalDateTime; // LocalDateTime 임포트 추가
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j // ⭐ @Slf4j 어노테이션 추가
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SquadService { // 클래스 이름은 기존대로 유지

    TeamMemberRepository teamMemberRepository;
    TeamRepository teamRepository;
    SquadRepository squadRepository;
    SquadPositionRepository squadPositionRepository;


    @Transactional
    public Optional<SquadResponse> getSquadByTeamIdAndFormationType(Long teamId, String formationType, Long userId) {
        // 사용자 권한 확인: 특정 팀의 스쿼드를 조회할 권한이 있는지 확인
        if (!teamMemberRepository.existsByUserIdAndTeamId(userId, teamId)) {
            throw new SecurityException("User is not authorized to view squads for this team.");
        }


        return squadRepository.findByTeamIdAndFormationTypeWithPositionsAndMembers(teamId, formationType)
                .map(this::convertToSquadResponse);
    }


    @Transactional
    public SquadResponse saveOrUpdateSquad(SquadRequestDto squadRequestDto, Long userId) {
        Team team = teamRepository.findById(squadRequestDto.getTeamId())
                .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + squadRequestDto.getTeamId()));

        // 사용자 권한 확인: 해당 팀의 스쿼드를 관리할 권한이 있는지 확인
        if (!teamMemberRepository.existsByUserIdAndTeamId(userId, team.getId())) {
            throw new SecurityException("User is not authorized to manage squads for this team.");
        }

        // 1. teamId와 formationType으로 기존 스쿼드 조회
        Optional<Squad> existingSquadOptional = squadRepository.findByTeamIdAndFormationType(
                squadRequestDto.getTeamId(), squadRequestDto.getFormationType());

        Squad squad;
        if (existingSquadOptional.isPresent()) {
            // 2. 기존 스쿼드가 존재하면 업데이트
            squad = existingSquadOptional.get();
            // 기존 포지션 목록을 모두 지웁니다 (orphanRemoval = true 덕분에 DB에서도 삭제됨)
            squad.getSquadPositions().clear();
            squad.setFormationType(squadRequestDto.getFormationType());
            squad.setIsDefault(true); // 업데이트 시에도 기본 설정 유지 (또는 특정 로직에 따라)
            squad.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 설정

        } else {
            // 3. 기존 스쿼드가 없으면 새로 생성
            squad = new Squad();
            squad.setTeam(team);
            squad.setFormationType(squadRequestDto.getFormationType());
            squad.setIsDefault(true); // 새로운 스쿼드를 해당 포메이션의 기본으로 간주
            squad.setCreatedAt(LocalDateTime.now()); // 생성 시간 설정
            squad.setUpdatedAt(LocalDateTime.now()); // 업데이트 시간 설정
        }


        log.debug("--- DEBUG: saveOrUpdateSquad method ---");
        log.debug("Squad object (after setting Team): {}", squad);
        if (squad.getTeam() != null) {
            log.debug("Squad's Team object: {}", squad.getTeam());
            log.debug("Squad's Team ID: {}", squad.getTeam().getId());
        } else {
            log.debug("Squad's Team is NULL!");
        }
        log.debug("--- DEBUG End ---");


        // 4. 새로운 포지션 정보 설정
        for (SquadPositionRequestDto dto : squadRequestDto.getPositionRequestDtoList()) {
            TeamMember teamMember = null;
            String playerName = null; // username을 위한 변수 추가 (SquadPosition에 username 필드가 있다면)

            if (dto.getTeamMemberId() != null) {
                teamMember = teamMemberRepository.findById(dto.getTeamMemberId())
                        .orElseThrow(() -> new EntityNotFoundException("TeamMember not found with ID: " + dto.getTeamMemberId()));

                if (teamMember.getUser() != null && teamMember.getUser().getUserInfo() != null) {
                    playerName = teamMember.getUser().getUserInfo().getName();
                }
            }

            SquadPosition squadPosition = SquadPosition.builder()
                    .fieldPositionCode(dto.getFieldPositionCode())
                    .teamMember(teamMember)
                    .username(playerName)
                    .squad(squad)
                    .team(squad.getTeam())
                    .build();
            squad.addSquadPosition(squadPosition); // 양방향 관계 설정
        }


        Squad savedSquad = squadRepository.save(squad);
        return convertToSquadResponse(savedSquad);
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
                            .playerName(playerName) // SquadPosition 엔티티에 저장된 username 대신 여기서 가져옴
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
    public List<SquadResponse> getSquadsByTeamId(Long teamId, Long userId) {
        if (!teamMemberRepository.existsByUserIdAndTeamId(userId, teamId)) {
            throw new SecurityException("User is not authorized to view squads for this team.");
        }

        List<Squad> squads = squadRepository.findByTeamIdWithPositionsAndMembers(teamId);
        return squads.stream()
                .map(this::convertToSquadResponse)
                .collect(Collectors.toList());
    }


}
