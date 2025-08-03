package com.example.careercubebackend.api.Squad.domain.entity;

import com.example.careercubebackend.api.SquadPosition.domain.SquadPosition;
import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "squads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Team 엔티티와 N:1 관계 설정
    private Team team; // String teamId 대신 Team 엔티티 직접 참조

    @Column(name = "formation_type", nullable = false)
    private String formationType;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SquadPosition> squadPositions = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addSquadPosition(SquadPosition squadPosition) {
        squadPositions.add(squadPosition);
        squadPosition.setSquad(this);
    }

    public void removeSquadPosition(SquadPosition squadPosition) {
        squadPositions.remove(squadPosition);
        squadPosition.setSquad(null);
    }
}