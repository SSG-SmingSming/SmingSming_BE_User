package com.smingsming.user.domain.user.contoller;

import com.smingsming.user.domain.user.entity.UserEntity;
import com.smingsming.user.domain.user.vo.*;
import com.smingsming.user.domain.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")
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
    @PostMapping("/signup")
    public SignUpResVo userSignUp(@RequestBody SignUpReqVo signUpReqVo) {

        return iUserService.userSignUp(signUpReqVo);
    }

    // 이메일 중복 여부 확인
    @GetMapping("/checkemail/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable(value = "email") String email) {
        boolean result = iUserService.checkEmail(email);

        if(result)
            return ResponseEntity.status(HttpStatus.OK).body(false);
        else
            return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    // 닉네임 중복 여부 확인
    @GetMapping("/checknickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable(value = "nickname") String nickname) {
        boolean result = iUserService.checkNickname(nickname);

        if(result)
            return ResponseEntity.status(HttpStatus.OK).body(false);
        else
            return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    // 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody PwdUpdateReqVo reqVo,
                                            HttpServletRequest request) {
        String newPwd = reqVo.getPassword();

        boolean result = iUserService.updatePassword(reqVo, request);

        if(result)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    // 프로필 사진 수정
    @PutMapping(value = "/update/thumbnail")
    public ResponseEntity<String> updateThumbnail(@RequestBody ThumbUpdateReqVo updateReqVo,
                                                  HttpServletRequest request) {
        boolean result = iUserService.updateThumbnail(updateReqVo, request);

        if (result)
            return ResponseEntity.status(HttpStatus.OK).body("수정 완료");
        else
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("수정 실패");
    }

    // 닉네임 수정
    @PutMapping("/update/nickname")
    public ResponseEntity<?> updateNickname(@RequestBody NickUpdateReqVo reqVo,
                                            HttpServletRequest request) {
        String newNickname = reqVo.getNickname();

        boolean result = iUserService.updateNickname(newNickname, request);

        if(result)
            return ResponseEntity.status(HttpStatus.OK).body(true);
        else
            return ResponseEntity.status(HttpStatus.OK).body(false);
    }

    @GetMapping("/get/{id}")
    public UserVo getUser(@PathVariable Long id) {
        UserEntity user = iUserService.getUser(id);
//        UserVo returnVo = ;

        return new ModelMapper().map(user, UserVo.class);
    }

}
