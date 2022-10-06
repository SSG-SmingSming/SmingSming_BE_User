package com.smingsming.user.domain.authmail.service;

import com.smingsming.user.domain.authmail.entity.AuthMailEntity;
import com.smingsming.user.domain.authmail.repository.IAuthMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class AuthMailServiceImpl implements IAuthMailService {

    private final JavaMailSender javaMailSender;
    private final IAuthMailRepository iAuthMailRepository;

    @Override
    public boolean sendKey(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        String authKey = String.valueOf(random.nextInt(999999));

        AuthMailEntity mailEntity = new AuthMailEntity(email, authKey);

        iAuthMailRepository.save(mailEntity);

        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("smingsming");
        simpleMailMessage.setSubject("인증번호");
        simpleMailMessage.setText(String.valueOf(authKey));

        javaMailSender.send(simpleMailMessage);

        return true;
    }

    @Override
    public boolean keyAuth(String email, String authKey) {
        String result = "";
        return iAuthMailRepository.existsByEmailAndAuthKey(email, authKey);
    }
}
