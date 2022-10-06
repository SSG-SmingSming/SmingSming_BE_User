package com.smingsming.user.domain.authmail.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCheckRequestVo {
    String email;
    String authKey;
}
