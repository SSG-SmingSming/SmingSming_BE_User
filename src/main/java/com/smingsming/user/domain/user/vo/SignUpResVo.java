package com.smingsming.user.domain.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResVo {

    private Long id;
    private String userEmail;
    private String nickname;
}
