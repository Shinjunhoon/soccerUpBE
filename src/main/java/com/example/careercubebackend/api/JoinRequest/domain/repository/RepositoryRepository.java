package com.example.careercubebackend.api.JoinRequest.domain.repository;

import com.example.careercubebackend.api.JoinRequest.domain.entitiy.TeamJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryRepository extends JpaRepository<TeamJoinRequest,Long> {

    List<TeamJoinRequest> findByTeamId(Long teamId);
    Optional<TeamJoinRequest> findByRequesterIdAndTeamId(Long requestId, Long teamId);
}
