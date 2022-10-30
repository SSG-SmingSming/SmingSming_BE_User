package com.smingsming.user.domain.user.service;

import com.smingsming.user.domain.user.dto.UserDto;
import com.smingsming.user.domain.user.entity.Role;
import com.smingsming.user.domain.user.entity.UserEntity;
import com.smingsming.user.domain.user.vo.*;
import com.smingsming.user.domain.user.repository.IUserRepository;
import com.smingsming.user.global.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
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
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setRole(Role.ROLE_USER.toString());
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

    // 닉네임 중복 확인
    @Override
    public boolean checkNickname(String nickname) {
        return iUserRepository.existsByNickname(nickname);
    }

    // 비밀번호 수정
    @Override
    @Transactional
    public boolean updatePassword(PwdUpdateReqVo pwdUpdateReqVo, HttpServletRequest request) {
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(jwtTokenProvider.resolveToken(request)));

        UserEntity userEntity = iUserRepository.findById(userId).orElseThrow();

        if(! bCryptPasswordEncoder.matches(pwdUpdateReqVo.getOldPassword(), userEntity.getPassword())) {
            return false;
        }

        userEntity.setPassword(bCryptPasswordEncoder.encode(pwdUpdateReqVo.getNewPassword()));

        return true;
    }

    // 프로필 사진 수정
    @Override
    @Transactional
    public boolean updateThumbnail(ThumbUpdateReqVo thumbUpdateReqVo, HttpServletRequest request) {

        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(jwtTokenProvider.resolveToken(request)));
        UserEntity user = iUserRepository.findById(userId).orElseThrow();

        user.updateThumbnail(thumbUpdateReqVo.getUserThumbnail());

        return true;
    }

    // 닉네임 수정
    @Override
    @Transactional
    public boolean updateNickname(String nickname, HttpServletRequest request) {
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(jwtTokenProvider.resolveToken(request)));
        UserEntity user = iUserRepository.findById(userId).orElseThrow();

        user.setNickname(nickname);

        return true;
    }

    // User 정보 조회
    @Override
    public UserDetailVo getUser(Long id) {
        UserEntity user = iUserRepository.findById(id).orElseThrow();

        return  UserDetailVo.builder()
                .id(user.getId())
                .nickName(user.getNickname())
                .userThumbnail(user.getUserThumbnail())
                .userEmail(user.getUserEmail())
                .build();
    }

    // User 검색
    @Override
    public List<UserDetailVo> searchUser(String keyword, int page, HttpServletRequest request) {
        Pageable pr = PageRequest.of(page - 1 , 20, Sort.by("id").descending());

        List<UserEntity> userList = iUserRepository.findAllByNicknameContains(pr, keyword);
        List<UserDetailVo> returnVo = new ArrayList<>();

        userList.forEach(user -> {
            returnVo.add(UserDetailVo.builder()
                    .id(user.getId())
                    .nickName(user.getNickname())
                    .userThumbnail(user.getUserThumbnail())
                    .userEmail(user.getUserEmail())
                    .build()) ;
        });

        return returnVo;
    }

}
