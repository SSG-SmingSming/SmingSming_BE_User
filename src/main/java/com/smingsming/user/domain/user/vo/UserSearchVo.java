package com.smingsming.user.domain.user.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserSearchVo {
    long count;
    List<UserDetailVo> result;
}
