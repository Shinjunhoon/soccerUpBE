package com.example.careercubebackend.api.Squad.application.impl;


import com.example.careercubebackend.api.Squad.domain.entity.Squad;
import com.example.careercubebackend.api.Squad.domain.repository.SquadRepository;
import com.example.careercubebackend.api.Squad.dto.SquadResponse;
import com.example.careercubebackend.api.SquadPosition.dto.SquadPositionResponse;
import com.example.careercubebackend.api.Team.domain.repository.TeamRepository;
import com.example.careercubebackend.api.TeamMember.domain.repository.TeamMemberRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class SquadGerService implements com.example.careercubebackend.api.Squad.application.SquadGerService {

    SquadRepository squadRepository;
    TeamRepository teamRepository;
    TeamMemberRepository teamMemberRepository;


    private SquadResponse convertToSquadResponse(Squad squad) {


        List<SquadPositionResponse> positionResponses = squad.getSquadPositions().stream()
                .map(pos -> {
                    String playerName = (pos.getTeamMember() != null) ? pos.getTeamMember().getUser().getUserInfo().getName() : null;
                    Long teamMemberId = (pos.getTeamMember() != null) ? pos.getTeamMember().getId() : null;
                    // playerActualPosition, playerRating은 SquadPosition 엔티티에 필드가 있다면 가져오고,
                    // 없다면 TeamMember에서 가져오거나 (TeamMember에 해당 정보가 있다면) null로 처리합니다.
                    // pos.getPlayerActualPosition()이 Position enum 타입이라면 .name()으로 String 변환


                    return SquadPositionResponse.builder() // Builder 사용 권장
                            .id(pos.getId())
                            .fieldPositionCode(pos.getFieldPositionCode())
                            .teamMemberId(teamMemberId)
                            .playerName(playerName)
                            .build();
                })
                .collect(Collectors.toList());

        return SquadResponse.builder() // Builder 사용 권장
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
    public List<SquadResponse> getSquadByTeamId(Long teamId, Long userId) {

        if (!teamMemberRepository.existsByUserIdAndTeamId(userId, teamId)) {
            throw new SecurityException("User is not authorized to manage squads for this team.");
        }
        List<Squad> squads = squadRepository.findByTeamId(teamId);

        if (squads.isEmpty()) {
            return List.of();
        }

        return squads.stream().map(this::convertToSquadResponse).collect(Collectors.toList());
    }
}
