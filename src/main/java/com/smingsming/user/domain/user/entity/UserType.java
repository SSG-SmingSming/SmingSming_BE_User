package com.smingsming.user.domain.user.entity;

public enum UserType {

    NORMAL(1), Google(2);

    private final int type;

    UserType(int type) {
        this.type = type;
    }
}