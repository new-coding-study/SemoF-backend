package com.loung.semof.member.service;

import com.loung.semof.exception.DuplicatedUsernameException;
import com.loung.semof.exception.LoginFailedException;
import com.loung.semof.jwt.TokenProvider;
import com.loung.semof.member.dao.MemberMapper;
import com.loung.semof.member.dto.MemberDto;
import com.loung.semof.member.dto.TokenDto;
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

    public AuthService(MemberMapper memberMapper, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public MemberDto signup(MemberDto memberDto) {
        log.info("[AuthService] Signup Start ===================================");
        log.info("[AuthService] MemberRequestDto {}", memberDto);
//아이디가 중복되는지 확인
        if(memberMapper.selectByMemberId(memberDto.getMemberId()) != null) {
            log.info("[AuthService] 아이디가 중복됩니다.");
            throw new DuplicatedUsernameException("아이디가 중복됩니다.");
        }
//        비밀번호 확인은 앞단?
//        주민번호 받아서? 뭐해야함?


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
    public TokenDto login(MemberDto memberDto) {
        log.info("[AuthService] Login Start ===================================");
        log.info("[AuthService] {}", memberDto);

        // 1. 아이디 조회
        MemberDto member = memberMapper.findByMemberId(memberDto.getMemberId())
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
