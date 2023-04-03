package com.loung.semof.loginInfo.service;


import com.loung.semof.loginInfo.dao.LoginInfoMapper;
import com.loung.semof.loginInfo.dto.LoginInfoDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Slf4j
@Service
public class MemberService {
    private final LoginInfoMapper loginInfoMapper;

    public MemberService(LoginInfoMapper loginInfoMapper) {
        this.loginInfoMapper = loginInfoMapper;
    }

    @GetMapping
    public LoginInfoDto selectMyInfo(@PathVariable String memberId) {
        log.info("[MemberService] getMyInfo Start ==============================");

        LoginInfoDto member = loginInfoMapper.selectByMemberId(memberId);
        log.info("[MemberService] {}", member);
        log.info("[MemberService] getMyInfo End ==============================");

        return member;
    }
}