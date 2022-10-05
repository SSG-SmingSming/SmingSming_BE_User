package com.smingsming.user.domain.user.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginReqVo {

    @Email
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    private String userEmail;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8)
    private String password;

}
