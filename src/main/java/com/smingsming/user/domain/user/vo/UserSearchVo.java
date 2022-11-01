package com.smingsming.user.domain.user.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserSearchVo {
    int count;
    List<UserDetailVo> result;
}
