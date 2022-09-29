package com.smingsming.user.domain.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseVo {

    private Long id;
    private String userEmail;
    private String nickname;
}
