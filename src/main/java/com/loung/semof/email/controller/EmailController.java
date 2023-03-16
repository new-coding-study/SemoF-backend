package com.loung.semof.email.controller;

import com.loung.semof.email.dto.EmailDto;
import com.loung.semof.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName : EmailController.java
 * @Project : SemoF
 * @version : 1.0.0
 * @Date : 2023-03-16
 * @작성자 : 이현도
 * @프로그램 설명 : 어플리케이션 컨텍스트 리소스의 위치 혹은 컨텍스트를 로드할때 사용되는 클래스의 컴포넌트를 선언
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * @작성일 : 2023-03-16
     * @작성자 : 이현도
     * @메소드설명 : 이메일 발송을 위한 코드를 작성하며, 프론트와 EmailService를 연결하는 기능을 한다.
     */
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto) {
//        emailService.saveEmail(emailDto);
//        emailService.sendEmail(emailDto);
        return ResponseEntity.ok("Email sent successfully");
    }
}

