package com.example.careercubebackend.api.user.domain.entity;


/*import com.jdh.springSecurityJwt.api.user.domain.entity.value.RoleInfo;*/


import com.example.careercubebackend.api.Team.domain.entitiy.Team;
import com.example.careercubebackend.api.TeamMember.domain.entitiy.TeamMember;
import com.example.careercubebackend.api.common.entity.RegModDt;
import com.example.careercubebackend.api.user.domain.entity.value.LoginInfo;
import com.example.careercubebackend.api.user.domain.entity.value.UserInfo;
import com.example.careercubebackend.api.user.dto.request.UserAddRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE user SET del_yn = true WHERE id = ?")
@SQLRestriction("del_yn = false")
public class User extends RegModDt {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Embedded
        private LoginInfo loginInfo;

//    @Embedded
//    private RoleInfo roleInfo;

        @Embedded
        private UserInfo userInfo;

        private boolean delYn = Boolean.FALSE; // 삭제 여부 기본값 false



    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Team> teams = new ArrayList<>();

      // 팀 멤버로도 참여할 수 있음
      @OneToMany(mappedBy = "user")
      private List<TeamMember> teamMemberships = new ArrayList<>();

    public static User of(UserAddRequestDTO dto) {
        // Login Info
        LoginInfo inputLoginInfo = LoginInfo.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .build();

        // User Info
        UserInfo inputUserInfo = UserInfo.builder()
                .name(dto.getName())
                .region(dto.getRegion())
                .email(dto.getEmail())
                .age(dto.getAge())
                .build();



        return User.builder()
                .loginInfo(inputLoginInfo)
                .userInfo(inputUserInfo)

                .build();
    }

}
