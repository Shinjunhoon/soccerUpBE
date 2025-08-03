package com.example.careercubebackend.api.SquadPosition.domain;

import com.example.careercubebackend.api.Squad.domain.entity.Squad;
import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "squad_positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SquadPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "squad_id", nullable = false)
    private Squad squad;

    private String username;

    @Column(name = "field_position_code", nullable = false)
    private String fieldPositionCode; // 예: "gk", "cb1", "st", "lw"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_member_id",nullable = false)
    private TeamMember teamMember;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}