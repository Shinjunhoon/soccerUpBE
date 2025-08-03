package com.example.careercubebackend.api.Squad.domain.repository;

import com.example.careercubebackend.api.Squad.domain.entity.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SquadRepository extends JpaRepository<Squad, Long> {
    public List<Squad> findByTeamId(Long teamId);
    @Query("SELECT s FROM Squad s LEFT JOIN FETCH s.squadPositions sp LEFT JOIN FETCH sp.teamMember tm LEFT JOIN FETCH tm.user u LEFT JOIN FETCH u.userInfo ui WHERE s.team.id = :teamId AND s.formationType = :formationType")
    Optional<Squad> findByTeamIdAndFormationTypeWithPositionsAndMembers(@Param("teamId") Long teamId, @Param("formationType") String formationType);


    Optional<Squad> findByTeamIdAndFormationType(Long teamId, String formationType);


    @Query("SELECT s FROM Squad s LEFT JOIN FETCH s.squadPositions sp LEFT JOIN FETCH sp.teamMember tm LEFT JOIN FETCH tm.user u LEFT JOIN FETCH u.userInfo ui WHERE s.team.id = :teamId")
    List<Squad> findByTeamIdWithPositionsAndMembers(@Param("teamId") Long teamId);
}
