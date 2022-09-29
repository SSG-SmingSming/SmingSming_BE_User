package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.dto.UserDto;
import com.smingsming.user.domain.user.vo.SignUpRequestVo;
import com.smingsming.user.domain.user.vo.SignUpResponseVo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    // 기본 회원가입
    SignUpResponseVo userSignUp(SignUpRequestVo signUpRequestVo);

    UserDto getUserByUserId(String userId);
    UserDto getUserDetailsByEmail(String userEmail);
}
