package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.dto.UserDto;
import com.smingsming.user.domain.user.entity.UserEntity;
import com.smingsming.user.domain.user.vo.PwdUpdateReqVo;
import com.smingsming.user.domain.user.vo.SignUpReqVo;
import com.smingsming.user.domain.user.vo.SignUpResVo;
import com.smingsming.user.domain.user.repository.IUserRepository;
import com.smingsming.user.global.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 기본 회원가입
    @Override
    public SignUpResVo userSignUp(SignUpReqVo signUpReqVo) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(signUpReqVo, UserEntity.class);
        
        if(iUserRepository.existsByUserEmail(userEntity.getUserEmail()) == true)
            throw new RuntimeException("이미 존재하는 계정입니다.");
        
        if(iUserRepository.existsByNickname(userEntity.getNickname()) == true)
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        
        userEntity.setPassword(bCryptPasswordEncoder.encode(signUpReqVo.getPassword()));
        iUserRepository.save(userEntity);

        SignUpResVo returnVo = new ModelMapper().map(userEntity, SignUpResVo.class);
        return returnVo;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        return null;
    }

    @Override
    public UserDto getUserDetailsByEmail(String userEmail) {

        UserEntity userEntity =  iUserRepository.findByUserEmail(userEmail);

        if(userEntity == null)
            throw new UsernameNotFoundException(userEmail);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = iUserRepository.findByUserEmail(username);

        if (userEntity == null) throw new UsernameNotFoundException(username);

        return new User(
                userEntity.getUserEmail(),
                userEntity.getPassword(),
                new ArrayList<>()
        );
    }

    // 이메일 중복 확인
    @Override
    public boolean checkEmail(String email) {
        return iUserRepository.existsByUserEmail(email);
    }


    @Override
    public boolean checkNickname(String nickname) {
        return iUserRepository.existsByNickname(nickname);
    }

    // 비밀번호 수정
    @Override
    @Transactional
    public boolean updatePassword(String password, HttpServletRequest request) {
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(jwtTokenProvider.resolveToken(request)));

        UserEntity userEntity = iUserRepository.findById(userId).orElseThrow();

        userEntity.setPassword(bCryptPasswordEncoder.encode(password));

        return true;
    }
}
