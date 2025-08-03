package com.example.careercubebackend.api.Team.domain.repository;

import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {

    public List<Team> findByOwnerId(Long id);

    Optional<Team> findByInviteCode(String inviteCode);

    @Query("SELECT DISTINCT t FROM Team t " +
            "LEFT JOIN TeamMember tm ON tm.team.id = t.id " +
            "WHERE t.owner.id = :userId OR tm.user.id = :userId")
    List<Team> findAllTeamsByOwnerOrMemberId(@Param("userId") Long userId);
    boolean existsByOwner_Id(Long ownerId);

    List<Team> findByOwner_Id(Long ownerId);
}

