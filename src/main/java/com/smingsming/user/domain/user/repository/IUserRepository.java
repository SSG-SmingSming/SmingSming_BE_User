package com.smingsming.user.domain.user.repository;

import com.smingsming.user.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
