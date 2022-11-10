package com.smingsming.user.global.config.oauth.service;

import com.smingsming.user.domain.user.entity.UserEntity;
import com.smingsming.user.domain.user.entity.UserType;
import com.smingsming.user.domain.user.repository.IUserRepository;
import com.smingsming.user.global.config.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IUserRepository iUserRepository;


    // Google로부터 받은 userRequest 데이터에 대한 후처리가 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration" +userRequest.getClientRegistration()); // registrationId 로 어떤 OAuth 로 로그인 했는지 확인 가능
        System.out.println("getAccessToken" +userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
        // userRequest 정보 -> 회원 프로필 받아야 함(loadUser 함수 호출) -> 구글로부터 회원 프로필 받아줌
        System.out.println("getAttributes" +oAuth2User.getAttributes());

        // 회원가입을 강제로 진행
        String provider = userRequest.getClientRegistration().getClientId();  // google
        String providerId = oAuth2User.getAttribute("sub");             // google 의 providerId (= sub)
        String username = provider+"_"+providerId;                            // google_101247854334101319744 (username 충돌 방지)
        String password = bCryptPasswordEncoder.encode("smingsming");
        String email = oAuth2User.getAttribute("email");
        String role = "ROLE_USER";
        String picture = oAuth2User.getAttribute("picture");

        UserEntity userEntity = iUserRepository.findByUserEmail(email);

        // Google 회원가입 후, 16자리 영문+숫자 랜덤 조합으로 기본 닉네임 설정
        Random rnd = new Random();

        StringBuffer buf;
        while (true) {
            buf = new StringBuffer();
            for(int i=2; i<=16; i++) {

                if(rnd.nextBoolean()){
                    buf.append((char)((int)(rnd.nextInt(26))+97));
                } else {
                    buf.append((rnd.nextInt(10)));
                }
            }

            boolean check = iUserRepository.existsByNickname(buf.toString());
            if(check == false) {
                break;
            }
        }

        System.out.println("닉네임 : " + buf.toString());


        if(userEntity == null) {
            userEntity = UserEntity.builder()
                    .userType(UserType.Google.ordinal())
                    .userEmail(email)
                    .password(password)
                    .nickname(buf.toString())
                    .role(role)
                    .membership(false)
                    .uuid(UUID.randomUUID().toString())
                    .build();
            iUserRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
