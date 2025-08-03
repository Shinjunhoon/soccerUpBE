package com.example.careercubebackend.api.user.domain.entity.value;

import com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum.Region;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Embeddable
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @Column(nullable = false)
    private String name;


    @NotNull(message = "나이는 필수 입력 항목입니다.") // age는 null이 될 수 없음
    @Min(value = 1, message = "나이는 1살 이상이어야 합니다.") // 최소 나이
    @Max(value = 150, message = "나이는 150살 이하여야 합니다.") // 최대 나이 (선택 사항)
    @Column(nullable = false) // JPA에게 'user' 테이블에 null을 허용하지 않는 'age' 컬럼을 만들라고 지시
    private Integer age;


    @Column(nullable = false) // DB에 Region 컬럼 생성
    private Region region;

    @Column(nullable = false)
    private String email;


}
