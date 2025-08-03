package com.example.careercubebackend.api.user.application.impl;


import com.example.careercubebackend.api.user.application.UserAddService;
import com.example.careercubebackend.api.user.domain.entity.User;
import com.example.careercubebackend.api.user.domain.repository.UserRepository;
import com.example.careercubebackend.api.user.dto.request.CheckUserIdDto;
import com.example.careercubebackend.api.user.dto.request.UserAddRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserAddServiceImpl implements UserAddService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    @Transactional
    public void addUser(final UserAddRequestDTO userAddRequestDTO) {
        // User DTO to Entity
        User user = User.of(userAddRequestDTO);

        // password 암호화
        user.getLoginInfo().encryptPassword(bCryptPasswordEncoder);

        // save
        userRepository.save(user);
    }

    @Override
    public boolean checkUserId(final CheckUserIdDto checkUserIdDto) {
       return ! userRepository.existsByLoginInfoUserId(checkUserIdDto.getUserId());
    }
}
