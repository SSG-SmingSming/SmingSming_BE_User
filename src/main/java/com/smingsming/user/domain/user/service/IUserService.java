package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.dto.RequestSignUpDto;
import com.smingsming.user.domain.user.dto.ResponseSignUpDto;

public interface IUserService {

    // 기본 회원가입
    ResponseSignUpDto userSignUp(RequestSignUpDto requestSignUpDto);

}
