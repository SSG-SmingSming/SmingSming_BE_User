package com.smingsming.user.domain.user.repository;

import com.smingsming.user.domain.user.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
    boolean existsByNickname(String nickname);
    List<UserEntity> findAllByNicknameContains(Pageable pr, String nickname);
}