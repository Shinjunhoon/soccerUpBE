package com.example.careercubebackend.api.TeamMember.domain.repository;


import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {

    List<TeamMember> findByTeam_Id(Long teamId);
    List<TeamMember> findByUser_Id(Long userId);
    boolean existsByUserIdAndTeamId(Long userId, Long teamId);
}
