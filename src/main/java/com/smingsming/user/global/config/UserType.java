package com.smingsming.user.global.config;

public enum UserType {

    NORMAL(1), Google(2);

    private final int type;

    UserType(int type) {
        this.type = type;
    }
}
