package com.smingsming.user.domain.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpReqVo {

    @Email
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    private String userEmail;

    @NotNull(message = "Nickname cannot be null")
    @Size(min = 2, message = "Nickname not be less than two characters")
    private String nickname;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8)
    private String Password;
}
