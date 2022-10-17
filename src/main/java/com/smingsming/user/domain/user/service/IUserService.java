package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.dto.UserDto;
import com.smingsming.user.domain.user.entity.UserEntity;
import com.smingsming.user.domain.user.vo.PwdUpdateReqVo;
import com.smingsming.user.domain.user.vo.SignUpReqVo;
import com.smingsming.user.domain.user.vo.SignUpResVo;
import com.smingsming.user.domain.user.vo.ThumbUpdateReqVo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IUserService extends UserDetailsService {


    SignUpResVo userSignUp(SignUpReqVo signUpReqVo);
    UserDto getUserByUserId(String userId);
    UserDto getUserDetailsByEmail(String userEmail);
    boolean checkEmail(String email);
    boolean checkNickname(String nickname);
    boolean updatePassword(PwdUpdateReqVo pwdUpdateReqVo, HttpServletRequest request);
    boolean updateThumbnail(ThumbUpdateReqVo thumbUpdateReqVo, HttpServletRequest request);
    boolean updateNickname(String nickname, HttpServletRequest request);
    UserEntity getUser(Long id);
}
