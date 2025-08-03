package com.example.careercubebackend.api.user.application.impl;


import com.example.careercubebackend.api.Team.application.impl.TeamGetServiceImpl;
import com.example.careercubebackend.api.Team.dto.TeamResponseJoinUrl;
import com.example.careercubebackend.api.user.application.UserGetService;
import com.example.careercubebackend.api.user.domain.entity.User;
import com.example.careercubebackend.api.user.domain.repository.UserRepository;
import com.example.careercubebackend.api.user.dto.response.UserGetResponseDTO;
import com.example.careercubebackend.api.user.exception.UserException;
import com.example.careercubebackend.api.user.exception.UserExceptionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserGetServiceImpl implements UserGetService {

    private final UserRepository userRepository;

    private final TeamGetServiceImpl teamGetService;


    @Override
    public UserGetResponseDTO getUserById(final long id) {

        List<TeamResponseJoinUrl> teamResponseJoinUrlList =   teamGetService.searchTeam(id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionResult.NOT_EXISTS));
       return UserGetResponseDTO.builder()
               .name(user.getUserInfo().getName())
               .email(user.getUserInfo().getEmail())
               .region(user.getUserInfo().getRegion())
               .age(user.getUserInfo().getAge())
               .teamResponseJoinUrlList(teamResponseJoinUrlList)
               .build();
    }

    @Override
    public User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionResult.NOT_EXISTS));
    }


    @Override
    public UserGetResponseDTO getUserByUserId(final String userId) {
        User user = userRepository.findByLoginInfoUserId(userId).orElseThrow(() -> new UserException(UserExceptionResult.NOT_EXISTS));
return UserGetResponseDTO.builder()
        .password(user.getLoginInfo().getPassword())
        .id(user.getId())
        .name(user.getUserInfo().getName())
        .build();

    }
}
