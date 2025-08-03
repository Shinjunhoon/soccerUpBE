package com.example.careercubebackend.api.SquadPosition;


import com.example.careercubebackend.api.SquadPosition.domain.SquadPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SquadPositionRepository extends JpaRepository<SquadPosition, Long> {
    List<SquadPosition> findBySquadId(Long squadId);
}