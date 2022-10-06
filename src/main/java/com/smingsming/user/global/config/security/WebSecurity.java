package com.smingsming.user.global.config.security;

import com.smingsming.user.domain.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private Environment env;
    private IUserService iuserService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    private CorsFilter corsFilter;

    @Autowired
    public WebSecurity(Environment env, IUserService iuserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.iuserService = iuserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests()
//                .antMatchers("/signup, /login").permitAll()
//                .antMatchers("/**").permitAll();
//        http.addFilter(getAuthenticationFilter());

        http.csrf().disable()
                    .headers().frameOptions().disable()
                .and()
                    .httpBasic().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/signup, /login").permitAll()
                    .antMatchers("/**").permitAll()
                .and()
                    .addFilter(getAuthenticationFilter());

    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), iuserService, env);
        authenticationFilter.setFilterProcessesUrl("/user/login");
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iuserService);
//                .passwordEncoder(bCryptPasswordEncoder);
    }
}
