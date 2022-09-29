package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.dto.UserDto;
import com.smingsming.user.domain.user.entity.UserEntity;
import com.smingsming.user.domain.user.vo.SignUpRequestVo;
import com.smingsming.user.domain.user.vo.SignUpResponseVo;
import com.smingsming.user.domain.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 기본 회원가입
    @Override
    public SignUpResponseVo userSignUp(SignUpRequestVo signUpRequestVo) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(signUpRequestVo, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(signUpRequestVo.getPassword()));
        iUserRepository.save(userEntity);

        SignUpResponseVo returnVo = new ModelMapper().map(userEntity, SignUpResponseVo.class);
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
}
