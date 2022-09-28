package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.domain.User;
import com.smingsming.user.domain.user.repository.IUserRepository;
import com.smingsming.user.domain.user.vo.SignUpRequestVo;
import com.smingsming.user.domain.user.vo.SignUpResponseVo;
import com.smingsming.user.global.config.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 기본 회원가입
    @Override
    public SignUpResponseVo userSignUp(SignUpRequestVo signUpRequestVo) {

        User user = iUserRepository.save(User.builder()
                .role("ROLE_USER")
                .userType(UserType.NORMAL.ordinal())
                .userEmail(signUpRequestVo.getUserEmail())
                .password(signUpRequestVo.getPassword())
                .nickname(signUpRequestVo.getNickname())
                .build());

        SignUpResponseVo returnDto = new ModelMapper().map(user, SignUpResponseVo.class);
        return returnDto;
    }
}
