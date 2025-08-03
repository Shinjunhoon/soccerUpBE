package com.example.careercubebackend.api.JoinRequest.domain.entitiy;


import com.example.careercubebackend.api.JoinRequest.domain.enums.Position;
import com.example.careercubebackend.api.JoinRequest.domain.enums.RequestStatus;
import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.Region;
import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.SkillLevel;
import com.example.careercubebackend.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Setter
@Getter
public class TeamJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING) // Enum의 이름을 (예: "SEOUL", "GYEONGGI") 문자열로 DB에 저장
    @Column(nullable = false, length = 20) // 시/도 코드의 이름을 저장하기 위한 적절한 길이
    private Region province; // Region enum 사용 (시/도 정보)

    @Column(length = 20) // 시/군/구 코드 (예: GANGNAM, SUWON)를 문자열로 저장
    private String cityCode; // 시/군/구 정보 (선택 사항이므로 nullable=false는 제거)

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SkillLevel skillLevel; // SkillLevel enum 사용

    @Column(length = 500)
    private String teamIntro; // 팀 소개는 텍스트이므로 String 유지

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus = RequestStatus.PENDING;


}
