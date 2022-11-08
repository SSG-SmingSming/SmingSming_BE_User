package com.smingsming.user.global.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smingsming.user.domain.user.entity.UserEntity;
import com.smingsming.user.global.common.jwt.JwtTokenProvider;
import com.smingsming.user.global.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        UserEntity userDto = new ModelMapper().map(((PrincipalDetails) oAuth2User).userEntity, UserEntity.class);

        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);
        // 최초 로그인이라면 회원가입 처리를 한다.
        log.info("토큰 발행 시작");

        String token = tokenService.createToken(userDto.getId(), userDto.getUuid());
        String targetUrl = "http://127.0.0.1:3000/oauthRedirect?token="+token;
        log.info("{}", token);

        response.addHeader("token", token);
        response.addHeader("userId", userDto.getId().toString());
        response.addHeader("userNick", userDto.getNickname());
        response.setHeader("Access-Control-Expose-Headers", "token, userId, userNick");

        response.sendRedirect(targetUrl);

//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}