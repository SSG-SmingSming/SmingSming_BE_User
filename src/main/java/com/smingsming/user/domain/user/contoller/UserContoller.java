package com.smingsming.user.domain.user.contoller;

import com.smingsming.user.domain.user.vo.*;
import com.smingsming.user.domain.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user-server")
@RequiredArgsConstructor
public class UserContoller {

    private final IUserService iUserService;

    @Autowired
    Environment env;

    // 서버 통신 상태 확인
    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    // 기본 회원가입
    @PostMapping("/user/signup")
    public SignUpResVo userSignUp(@RequestBody SignUpReqVo signUpReqVo) {

        return iUserService.userSignUp(signUpReqVo);
    }

    // 이메일 중복 여부 확인
    @GetMapping("/user/checkemail")
    public ResponseEntity<?> checkEmail(@RequestBody EmailCheckReqVo checkRequestVo) {
        String email = checkRequestVo.getEmail();

        boolean result = iUserService.checkEmail(email);

        if(result)
            return ResponseEntity.status(HttpStatus.OK).body(false);
        else
            return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    // 닉네임 중복 여부 확인
    @GetMapping("/user/checknickname")
    public ResponseEntity<?> checkNickname(@RequestBody NicknameCheckReqVo checkRequestVo) {
        String nickname = checkRequestVo.getNickname();

        boolean result = iUserService.checkNickname(nickname);

        if(result)
            return ResponseEntity.status(HttpStatus.OK).body(false);
        else
            return ResponseEntity.status(HttpStatus.OK).body(true);
    }


    @PutMapping("/user/update/password")
    public ResponseEntity<?> updatePassword(@RequestBody PwdUpdateReqVo reqVo,
                                            HttpServletRequest request) {
        String newPwd = reqVo.getPassword();

        boolean result = iUserService.updatePassword(newPwd, request);

        if(result)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.OK).body(false);
    }
}
