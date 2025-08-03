package com.example.careercubebackend.api.user.application;


import com.example.careercubebackend.api.user.domain.entity.User;
import com.example.careercubebackend.api.user.dto.response.UserGetResponseDTO;

public interface UserGetService {


    UserGetResponseDTO getUserById(final long id);

    User getUserById(final Long id);


    UserGetResponseDTO getUserByUserId(final String userId);

}
