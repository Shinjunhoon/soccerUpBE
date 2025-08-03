package com.example.careercubebackend.api.Squad.controller;


import com.example.careercubebackend.api.Squad.application.SquadGerService;
import com.example.careercubebackend.api.Squad.application.impl.SquadAddService;
import com.example.careercubebackend.api.Squad.application.impl.SquadService;
import com.example.careercubebackend.api.Squad.dto.SquadRequestDto;
import com.example.careercubebackend.api.Squad.dto.SquadResponse;
import com.example.careercubebackend.api.common.response.entity.ApiResponseEntity;
import com.example.careercubebackend.jwt.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RestController
public class SquadController {

    SquadAddService squadAddService;
    SquadGerService squadGerService;
    SquadService squadService;

    @PostMapping("/saveSquad")
    public ResponseEntity<ApiResponseEntity> saveSquad(@RequestBody SquadRequestDto request, Authentication authentication) {
        return  ApiResponseEntity.successResponseEntity( squadAddService.addSquad(request,JwtUtil.getLoginId(authentication)));

    }
    @PutMapping("/updateSquad/{squadId}")
    public ResponseEntity<ApiResponseEntity> upadeSquad(@PathVariable Long squadId , @RequestBody SquadRequestDto request, Authentication authentication) {

        return  ApiResponseEntity.successResponseEntity(squadAddService.updateSquad(squadId, request,JwtUtil.getLoginId(authentication)));
    }

    @GetMapping("/searchSquad/{teamId}")
    public ResponseEntity<ApiResponseEntity> searchSquad(@PathVariable Long teamId, Authentication authentication) {
        return ApiResponseEntity.successResponseEntity(squadGerService.getSquadByTeamId(teamId,JwtUtil.getLoginId(authentication)));
    }

    @GetMapping("/team/{teamId}/byFormation")
    public ResponseEntity<ApiResponseEntity> getSquadByTeamIdAndFormation( // ✨ 반환 타입 변경
                                                                           @PathVariable Long teamId,
                                                                           @RequestParam String formationType,
                                                                           Authentication authentication) {
        try {
            Long userId = JwtUtil.getLoginId(authentication);
            Optional<SquadResponse> squadOptional = squadService.getSquadByTeamIdAndFormationType(teamId, formationType, userId);

            if (squadOptional.isPresent()) {
                return ApiResponseEntity.successResponseEntity(squadOptional.get()); // ✨ ApiResponseEntity 헬퍼 메서드 사용
            } else {
                // 해당 포메이션의 스쿼드가 없으면 404 Not Found 반환
                // ApiResponseEntity는 기본적으로 200 OK를 반환하므로,
                // ResponseEntity.status(HttpStatus.NOT_FOUND)를 직접 사용하여 HTTP 상태 코드 변경
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseEntity.failResponseEntity("Squad not found for team " + teamId + " and formation " + formationType).getBody()); // ✨ failResponseEntity 사용
            }
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponseEntity.failResponseEntity(e.getMessage()).getBody()); // ✨ failResponseEntity 사용
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseEntity.failResponseEntity("Failed to retrieve squad by formation: " + e.getMessage()).getBody()); // ✨ failResponseEntity 사용
        }
    }


    @PostMapping("/saveOrUpdate")
    public ResponseEntity<ApiResponseEntity> saveOrUpdateSquad( // ✨ 반환 타입 변경
                                                                @RequestBody SquadRequestDto squadRequestDto,
                                                                Authentication authentication) {
        try {
            Long userId = JwtUtil.getLoginId(authentication);
            SquadResponse savedOrUpdatedSquad = squadService.saveOrUpdateSquad(squadRequestDto, userId);
            return ApiResponseEntity.successResponseEntity(savedOrUpdatedSquad); // ✨ ApiResponseEntity 헬퍼 메서드 사용
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseEntity.failResponseEntity(e.getMessage()).getBody()); // ✨ failResponseEntity 사용
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponseEntity.failResponseEntity(e.getMessage()).getBody()); // ✨ failResponseEntity 사용
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseEntity.failResponseEntity("Failed to save/update squad: " + e.getMessage()).getBody()); // ✨ failResponseEntity 사용
        }
    }


    @GetMapping("/team/{teamId}")
    public ResponseEntity<ApiResponseEntity> getSquadsByTeamId( // ✨ 반환 타입 변경
                                                                @PathVariable Long teamId,
                                                                Authentication authentication) {
        try {
            Long userId = JwtUtil.getLoginId(authentication);
            List<SquadResponse> squads = squadService.getSquadsByTeamId(teamId, userId);
            return ApiResponseEntity.successResponseEntity(squads); // ✨ ApiResponseEntity 헬퍼 메서드 사용
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponseEntity.failResponseEntity(e.getMessage()).getBody()); // ✨ failResponseEntity 사용
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseEntity.failResponseEntity("Failed to retrieve squads: " + e.getMessage()).getBody()); // ✨ failResponseEntity 사용
        }
    }
}