package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.domain.User;
import com.smingsming.user.domain.user.dto.RequestSignUpDto;
import com.smingsming.user.domain.user.dto.ResponseSignUpDto;
import com.smingsming.user.domain.user.repository.IUserRepository;
import com.smingsming.user.global.config.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 기본 회원가입
    @Override
    public ResponseSignUpDto userSignUp(RequestSignUpDto requestSignUpDto) {
        User user = iUserRepository.save(User.builder()
                .role("ROLE_USER")
                .userType(UserType.NORMAL.ordinal())
                .userEmail(requestSignUpDto.getUserEmail())
                .password(requestSignUpDto.getPassword())
                .nickname(requestSignUpDto.getNickname())
                .build());

        ResponseSignUpDto returnDto = new ModelMapper().map(user, ResponseSignUpDto.class);
        return returnDto;
    }
}
