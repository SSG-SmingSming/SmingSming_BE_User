package com.smingsming.user.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSignUpDto {

    private Long id;
    private String userEmail;
    private String nickname;
}
