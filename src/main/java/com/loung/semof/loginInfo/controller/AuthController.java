package com.loung.semof.loginInfo.controller;


import com.loung.semof.common.ResponseDto;
import com.loung.semof.loginInfo.dto.LoginInfoDto;

import com.loung.semof.loginInfo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/find-reg")
    public ResponseEntity<ResponseDto> checkEmpReg(@RequestBody String empReg){
        System.out.println("empReg = " + empReg);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "주민번호 검증", authService.checkEmpReg(empReg)));
    }
    @PostMapping("/compare-id")
    public ResponseEntity<ResponseDto> checkId(@RequestBody String loginId){


        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "아이디 확인", authService.checkId(loginId)));
    }
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody LoginInfoDto loginInfoDto) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "회원가입 성공", authService.signup(loginInfoDto)));
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginInfoDto memberDto) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "로그인 성공", authService.login(memberDto)));
    }

}