package com.smingsming.user.domain.authmail.service;

public interface IAuthMailService {
    boolean sendKey(String email);

    boolean keyAuth(String email, String authKey);
}
