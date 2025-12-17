package com.example.careercubebackend.api.TeamMember.domain.repository;


import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {

    List<TeamMember> findByTeam_Id(Long teamId);
    List<TeamMember> findByUser_Id(Long userId);
    boolean existsByUserIdAndTeamId(Long userId, Long teamId);
    @Query("SELECT tm FROM TeamMember tm JOIN FETCH tm.team WHERE tm.user.id = :userId")
    List<TeamMember> findByUserWithTeam(@Param("userId") Long userId);

    @Query("SELECT tm FROM TeamMember tm JOIN FETCH tm.user u WHERE tm.team.id = :teamId")
    List<TeamMember> findByTeamWithUser(@Param("teamId") Long teamId);




}
