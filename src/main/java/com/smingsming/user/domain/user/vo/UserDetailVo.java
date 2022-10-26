package com.smingsming.user.domain.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailVo {
    private Long id;
    private String userEmail;
    private String nickName;
    private String userThumbnail;
}