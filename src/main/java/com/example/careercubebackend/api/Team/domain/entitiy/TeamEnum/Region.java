package com.example.careercubebackend.api.Team.domain.entitiy.TeamEnum; // ⭐ 실제 패키지 경로로 변경하세요.

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Region {
    // 주요 시/도 코드와 한글명 (변동 가능성이 거의 없음)
    SEOUL("서울"),
    BUSAN("부산"),
    DAEGU("대구"),
    INCHEON("인천"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),
    SEJONG("세종"),
    JEJU("제주"),
    GYEONGGI("경기"),
    GANGWON("강원"),
    CHUNGBUK("충북"),
    CHUNGNAM("충남"),
    JEONBUK("전북"),
    JEONNAM("전남"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남");

    private final String provinceName; // 시/도 한글명 (예: "서울", "경기")

    // Enum 값을 빠르게 찾기 위한 Map (성능 최적화: fromProvinceCode 메서드에서 사용)
    private static final Map<String, Region> BY_PROVINCE_CODE =
            Arrays.stream(values()).collect(Collectors.toMap(Region::name, Function.identity()));

    /**
     * 시/도 코드(String)를 Region Enum으로 변환합니다.
     * 예: "SEOUL" -> Region.SEOUL
     * @param code 시/도 코드 문자열
     * @return 해당 Region Enum 인스턴스
     * @throws IllegalArgumentException 유효하지 않은 코드일 경우
     */
    public static Region fromProvinceCode(String code) {
        Region region = BY_PROVINCE_CODE.get(code);
        if (region == null) {
            throw new IllegalArgumentException("유효하지 않은 시/도 코드입니다: " + code);
        }
        return region;
    }
}