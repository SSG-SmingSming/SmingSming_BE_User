package com.smingsming.user.global.config.security;

import com.smingsming.user.domain.user.service.IUserService;
import com.smingsming.user.global.config.Role;
import com.smingsming.user.global.config.handler.OAuth2SuccessHandler;
import com.smingsming.user.global.config.oauth.service.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final IUserService iuserService;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.addFilter(getAuthenticationFilter());
        http.authorizeRequests()
                    .antMatchers("/user/signup").permitAll()
                    .antMatchers("/error/**").permitAll()
                    .antMatchers("/**").permitAll()
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService)        // Google 로그인 완료된 뒤의 후처리 필요. Tip. 코드X, (엑세스토큰+사용자 프로필 정보O);
                .and()
                    .successHandler(oAuth2SuccessHandler);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), iuserService, env);
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iuserService);
    }
}
