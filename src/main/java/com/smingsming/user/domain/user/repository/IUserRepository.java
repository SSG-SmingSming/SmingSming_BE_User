package com.smingsming.user.domain.user.repository;

import com.smingsming.user.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
    boolean existsByNickname(String nickname);
}