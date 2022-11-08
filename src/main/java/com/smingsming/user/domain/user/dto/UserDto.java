package com.smingsming.user.domain.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String useEmail;
    private String password;
    private String nickname;
    private Date createAt;
    private String UUID;

    private String encryptedPwd;

}
