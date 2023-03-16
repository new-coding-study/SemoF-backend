package com.loung.semof.email.service;

import com.loung.semof.email.dao.EmailMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @파일이름 : EmailService.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-16
 * @작성자 : 이현도
 * @프로그램 설명 : 이메일 관련 요청을 처리할 Service의 구현체
 */
@Service
public class EmailService {

    private JavaMailSender emailSender;

    private final EmailMapper emailMapper;

    public EmailService(EmailMapper emailMapper) {
        this.emailMapper = emailMapper;
    }

//    public void saveEmail(EmailDto emailDto) {
//        emailDto.getEmail();
//        emailDto.getSubject();
//        emailDto.getBody();
//        emailMapper.insertEmail(email);
//    }
//
//    public void sendEmail(EmailDto emailDto) {
//    }
}
