package com.example.careercubebackend.api.Team.domain.entitiy;

// ... 기존 임포트 ...
import com.example.careercubebackend.api.Squad.domain.entity.Squad; // ⭐ Squad 임포트 추가

import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.AverageAge;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.Region;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.SkillLevel;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.TeamType;
import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import com.example.careercubebackend.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String teamName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @Column(unique = true, nullable = false, length = 8)
    private String inviteCode;


    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMember> members = new ArrayList<>();


    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Squad> squads = new ArrayList<>();

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Region province;

    @Column(length = 20)
    private String cityCode;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AverageAge averageAge;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private TeamType teamType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SkillLevel skillLevel;

    @Column(length = 500)
    private String teamIntro;

    @PrePersist
    public void prePersist() {
        if (this.inviteCode == null) {
            this.inviteCode = UUID.randomUUID().toString().substring(0, 8);
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public void addSquad(Squad squad) {
        this.squads.add(squad);
        squad.setTeam(this);
    }

    public void removeSquad(Squad squad) {
        this.squads.remove(squad);
        squad.setTeam(null);
    }
}