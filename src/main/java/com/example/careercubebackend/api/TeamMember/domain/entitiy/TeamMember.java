package com.example.careercubebackend.api.TeamMember.domain.entitiy;


import com.example.careercubebackend.api.JoinRequest.domain.enums.Position;
import com.example.careercubebackend.api.SquadPosition.domain.SquadPosition;
import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Position position;

    private LocalDateTime joinedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "teamMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SquadPosition> squadPositions = new ArrayList<>(); // 초기화
}
