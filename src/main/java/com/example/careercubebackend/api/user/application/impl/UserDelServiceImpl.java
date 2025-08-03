package com.example.careercubebackend.api.user.application.impl;


import com.example.careercubebackend.api.user.application.UserDelService;
import com.example.careercubebackend.api.user.domain.entity.User;
import com.example.careercubebackend.api.user.domain.repository.UserRepository;
import com.example.careercubebackend.api.user.exception.UserException;
import com.example.careercubebackend.api.user.exception.UserExceptionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDelServiceImpl implements UserDelService {

    private final UserRepository userRepository;


    @Override
    @Transactional
    public void delUser(final long id) {
        // 사용자 정보 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionResult.NOT_EXISTS));

        // delete
        userRepository.delete(user);
    }
}
