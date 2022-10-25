package com.smingsming.user.domain.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVo {
    private Long id;
    private String userEmail;
    private String nickName;
    private String userThumbnail;
}