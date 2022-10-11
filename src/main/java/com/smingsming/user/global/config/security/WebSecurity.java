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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private Environment env;
    private IUserService iuserService;
    private PrincipalOauth2UserService principalOauth2UserService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Autowired
    public WebSecurity(Environment env, IUserService iuserService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       PrincipalOauth2UserService principalOauth2UserService,
                       OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.env = env;
        this.iuserService = iuserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.principalOauth2UserService = principalOauth2UserService;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.addFilter(getAuthenticationFilter());
        http.authorizeRequests()
                    .antMatchers("/signup, /login").permitAll()
                    .antMatchers("/user-server/user/signup").permitAll()
                    .antMatchers("/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService)        // Google 로그인 완료된 뒤의 후처리 필요. Tip. 코드X, (엑세스토큰+사용자 프로필 정보O);
                .and()
                    .successHandler(oAuth2SuccessHandler);
//                    .failureHandler(configFailureHandler());

    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), iuserService, env);
        authenticationFilter.setFilterProcessesUrl("/user-server/user/login");
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iuserService);
    }
}
