package com.loung.semof.loginInfo.service;

import com.loung.semof.exception.DuplicatedUsernameException;
import com.loung.semof.exception.LoginFailedException;
import com.loung.semof.jwt.TokenProvider;
import com.loung.semof.loginInfo.dao.MemberMapper;
import com.loung.semof.loginInfo.dto.LoginInfoDto;

import com.loung.semof.loginInfo.dto.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {

    private final MemberMapper memberMapper;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

//    private final EmployeeDto employeeDto;

    public AuthService(MemberMapper memberMapper, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
//        this.employeeDto = employeeDto;
    }

    @Transactional
    public LoginInfoDto signup(LoginInfoDto memberDto) {
        log.info("[AuthService] Signup Start ===================================");
        log.info("[AuthService] MemberRequestDto {}", memberDto);

        if (memberMapper.selectByMemberId(memberDto.getMemberId()) != null) {
            log.info("[AuthService] 아이디가 중복됩니다.");
            throw new DuplicatedUsernameException("아이디가 중복됩니다.");
        }
//반복문 돌려서 ?
//        for(int i=0; i<employeeDto.getEmpReg().length();i++){
//
//        }

        if (memberMapper.selectByMemberReg(memberDto.getEmpReg()) != null) {
            log.info("[AuthService] 주민번호가 중복됩니다.");
            throw new DuplicatedUsernameException("주민번호가 중복됩니다.");
        }
        log.info("[AuthService] Member Signup Start ==============================");
        memberDto.setMemberPassword(passwordEncoder.encode(memberDto.getMemberPassword()));
        log.info("[AuthService] Member {}", memberDto);
//주민번호 유효성만 확인??
//        if (!passwordEncoder.matches(memberDto.getMemberPassword(), member.getMemberPassword())) {
//            log.info("[AuthService] Password Match Fail!!!!!!!!!!!!");
//            throw new LoginFailedException("잘못된 아이디 또는 비밀번호입니다");
//        }

        int result = memberMapper.insertMember(memberDto);
        log.info("[AuthService] Member Insert Result {}", result > 0 ? "회원 가입 성공" : "회원 가입 실패");

        log.info("[AuthService] Signup End ==============================");

        return memberDto;
    }


    @Transactional
    public TokenDto login(LoginInfoDto memberDto) {
        log.info("[AuthService] Login Start ===================================");
        log.info("[AuthService] {}", memberDto);

        // 1. 아이디 조회
        LoginInfoDto member = memberMapper.findByMemberId(memberDto.getMemberId())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디 또는 비밀번호입니다"));

        // 2. 비밀번호 매칭
        if (!passwordEncoder.matches(memberDto.getMemberPassword(), member.getMemberPassword())) {
            log.info("[AuthService] Password Match Fail!!!!!!!!!!!!");
            throw new LoginFailedException("잘못된 아이디 또는 비밀번호입니다");
        }

        // 3. 토큰 발급
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        log.info("[AuthService] tokenDto {}", tokenDto);

        log.info("[AuthService] Login End ===================================");

        return tokenDto;
    }
}


