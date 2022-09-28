package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.vo.SignUpRequestVo;
import com.smingsming.user.domain.user.vo.SignUpResponseVo;

public interface IUserService {

    // 기본 회원가입
    SignUpResponseVo userSignUp(SignUpRequestVo requestSignUpDto);

}
