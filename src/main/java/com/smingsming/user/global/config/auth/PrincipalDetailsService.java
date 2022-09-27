package com.smingsming.user.global.config.auth;

import com.smingsming.user.domain.user.domain.User;
import com.smingsming.user.domain.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// login 요청 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository iUserRepository;

    // Security session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        System.out.println("userEamil : " +userEmail);
        User user = iUserRepository.findByUserEmail(userEmail);
        if(userEmail !=null) {
            return new PrincipalDetails(user);
        }
        return null;
    }
}
