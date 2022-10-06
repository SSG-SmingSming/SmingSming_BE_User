package com.smingsming.user.domain.authmail.contoller;

import com.smingsming.user.domain.authmail.repository.IAuthMailRepository;
import com.smingsming.user.domain.authmail.service.IAuthMailService;
import com.smingsming.user.domain.authmail.vo.AuthCheckRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthMailController {

    private final IAuthMailService iAuthMailService;
    private final IAuthMailRepository iAuthMailRepository;

    @PostMapping("/send/{userEmail}")
    public ResponseEntity<?> sendKey(@PathVariable(value = "userEmail") String userEmail) {
        boolean result = iAuthMailService.sendKey(userEmail);
//        javaMailSender.send(simpleMailMessage);

        if (result)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    @PostMapping("/check")
    public ResponseEntity<?> keyAuth(@RequestBody AuthCheckRequestVo authCheckRequestVo) {
        boolean result = iAuthMailService.keyAuth(authCheckRequestVo.getEmail(), authCheckRequestVo.getAuthKey());

        if (result)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.OK).body(false);
    }
}
