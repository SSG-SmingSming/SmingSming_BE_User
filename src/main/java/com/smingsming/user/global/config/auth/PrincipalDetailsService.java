package com.smingsming.user.global.config.auth;

import com.smingsming.user.domain.user.entity.UserEntity;
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
//        return iUserRepository.findByUserEmail(userEmail)
//                .orElseThrow(() -> new UsernameNotFoundException(userEmail));
        UserEntity userEntity = iUserRepository.findByUserEmail(userEmail);
        if(userEmail !=null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
        }
}