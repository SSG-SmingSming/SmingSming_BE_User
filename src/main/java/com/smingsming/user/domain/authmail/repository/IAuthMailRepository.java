package com.smingsming.user.domain.authmail.repository;

import com.smingsming.user.domain.authmail.entity.AuthMailEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAuthMailRepository extends MongoRepository<AuthMailEntity, String> {
    boolean existsByEmailAndAuthKey(String email, String authKey);
}
