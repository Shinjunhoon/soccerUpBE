package com.example.careercubebackend.api.user.domain.repository;


import com.example.careercubebackend.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByLoginInfoUserId(final String userId);

    boolean existsByLoginInfoUserId(String loginId);

    Optional<User> findById(long id);

}
