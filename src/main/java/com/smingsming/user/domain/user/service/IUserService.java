package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.dto.UserDto;
import com.smingsming.user.domain.user.vo.PwdUpdateReqVo;
import com.smingsming.user.domain.user.vo.SignUpReqVo;
import com.smingsming.user.domain.user.vo.SignUpResVo;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

public interface IUserService extends UserDetailsService {

    // 기본 회원가입
    SignUpResVo userSignUp(SignUpReqVo signUpReqVo);

    UserDto getUserByUserId(String userId);
    UserDto getUserDetailsByEmail(String userEmail);

    // 이메일 중복 여부 확인
    boolean checkEmail(String email);

    // 닉네임 중복 여부 확인
    boolean checkNickname(String nickname);

    // 비밀번호 수정
    boolean updatePassword(String password, HttpServletRequest request);


}
