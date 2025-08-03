package com.example.careercubebackend.api.user.application;


import com.example.careercubebackend.api.user.dto.request.CheckUserIdDto;
import com.example.careercubebackend.api.user.dto.request.UserAddRequestDTO;

public interface UserAddService {


    void addUser(final UserAddRequestDTO userAddRequestDTO);

    boolean checkUserId(final CheckUserIdDto checkUserIdDto );
}
