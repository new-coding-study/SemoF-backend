package com.loung.semof.loginInfo.service;

import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.exception.DuplicatedUsernameException;
import com.loung.semof.exception.LoginFailedException;
import com.loung.semof.jwt.TokenProvider;
import com.loung.semof.loginInfo.dao.LoginInfoMapper;
import com.loung.semof.loginInfo.dto.LoginInfoDto;

import com.loung.semof.loginInfo.dto.TokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    private final LoginInfoMapper loginInfoMapper;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

//    private final EmployeeDto employeeDto;
    public Integer newEmpNo;
    public AuthService(LoginInfoMapper loginInfoMapper, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.loginInfoMapper = loginInfoMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
//        this.employeeDto = employeeDto;
    }

    @Transactional
    public LoginInfoDto signup(LoginInfoDto loginInfoDto) {

//        if (loginInfoMapper.selectByMemberId(memberDto.getMemberId()) != null) {
//            log.info("[AuthService] 아이디가 중복됩니다.");
//            throw new DuplicatedUsernameException("아이디가 중복됩니다.");
//        }

//        if (loginInfoMapper.selectByEmpReg(memberDto.getEmpReg()) != null) {
//            log.info("[AuthService] 주민번호가 중복됩니다.");
//            throw new DuplicatedUsernameException("주민번호가 중복됩니다.");
//        }
        log.info("[AuthService] Member Signup Start ==============================");
        loginInfoDto.setLoginPwd(passwordEncoder.encode(loginInfoDto.getLoginPwd()));
        log.info("[AuthService] Member {}", loginInfoDto);
        loginInfoDto.setEmpNo(newEmpNo);

        int result = loginInfoMapper.insertMember(loginInfoDto);
        log.info("[AuthService] Member Insert Result {}", result > 0 ? "회원 가입 성공" : "회원 가입 실패");

        log.info("[AuthService] Signup End ==============================");

        return loginInfoDto;
    }


    @Transactional
    public TokenDto login(LoginInfoDto memberDto) {
        log.info("[AuthService] Login Start ===================================");
        log.info("[AuthService] {}", memberDto);

        // 1. 아이디 조회
        LoginInfoDto member = loginInfoMapper.findByMemberId(memberDto.getLoginId())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디 또는 비밀번호입니다"));

        // 2. 비밀번호 매칭
        if (!passwordEncoder.matches(memberDto.getLoginPwd(), member.getLoginPwd())) {
            log.info("[AuthService] Password Match Fail!!!!!!!!!!!!");
            throw new LoginFailedException("잘못된 아이디 또는 비밀번호입니다");
        }

        // 3. 토큰 발급
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        log.info("[AuthService] tokenDto {}", tokenDto);

        log.info("[AuthService] Login End ===================================");

        return tokenDto;
    }

//    public Object selectByEmpReg() {
//        if (loginInfoMapper.selectByEmpReg(memberDto.getEmpReg()) != null) {
//            log.info("[AuthService] 주민번호가 중복됩니다.");
//            throw new DuplicatedUsernameException("주민번호가 중복됩니다.");
//        }
//    }

    public Object checkEmpReg(String empReg) {
////        if (loginInfoMapper.selectByEmpReg(empReg).getEmpReg() != null) {
//            log.info("[AuthService] 주민번호가 확인되었습니다.");
        System.out.println("empReg = " + empReg);
//        }
        EmployeeDto employeeDto = loginInfoMapper.selectByEmpReg(empReg);
        System.out.println("매퍼의 값 : " +loginInfoMapper.selectByEmpReg(empReg));
        System.out.println("employeeDto = " + employeeDto);
//        매퍼에서 empREg empoNo를 들고 온다,.
//        loginInfoMapper.selectByEmpReg(empReg) dto 형식으로 되어있음
//        empNo로는 setting을 하고 setEmpNo(여기서 가져온거)
//        empReg는 주민번호 검증에 사용
//        newEmpNo = Math.toIntExact(loginInfoMapper.selectByEmpReg(empReg).get().getEmpNo());
        newEmpNo = Math.toIntExact(loginInfoMapper.selectByEmpReg(empReg).getEmpNo());
//        newEmpNo = Math.toIntExact(loginInfoMapper.selectByEmpReg(empReg));
//        if(loginInfoMapper.selectByEmpReg(empReg).get().getEmpReg().equals(empReg)){
            if(loginInfoMapper.selectByEmpReg(empReg).getEmpReg().equals(empReg)){
            return "주민번호 체크 성공";
        }else{
            return "체크 실패";
        }


//        Optional<LoginInfoDto> loginInfoDtoOptional = loginInfoService.findByUsername("example");
//
//        if (loginInfoDtoOptional.isPresent()) {
//            String username = loginInfoDtoOptional.get().getUsername();
//            // username 필드를 이용하여 로직 수행
//        } else {
//            // 로직 수행 불가능
//        }
//        int result = loginInfoMapper.checkEmpReg(empReg);

//        return (result>0) ? "주민번호 체크 성공": "체크 실패";
//        return 1;
    }
//    public Object checkEmpReg(String empReg) {
//        List<UserDTO> userList = userMapper.getUserList();
//
//        for (UserDTO user : userList) {
//            if (user.getEmpReg().equals(empReg)) {
//                log.info("[AuthService] 주민번호가 중복됩니다.");
//                throw new DuplicatedUsernameException("주민번호가 중복됩니다.");
//            }
//        }
//
//        // empReg와 일치하는 UserDTO 객체를 찾을 수 없는 경우 null 반환
//        UserDTO foundUser = userList.stream().filter(user -> user.getEmpReg().equals(empReg)).findFirst().orElse(null);
//
//        if (foundUser == null) {
//            return "주민번호 중복체크 성공";
//        } else {
//            return "주민번호 중복체크 성공, userNo: " + foundUser.getUserNo();
//        }
//    }
    public Object checkId(String loginId) {
        if (loginInfoMapper.selectById(loginId) != null) {
            log.info("[AuthService] 아이디가 중복됩니다.");
            throw new DuplicatedUsernameException("아이디가 중복됩니다.");
//            return "아이디 중복체크 성공";
        }else{
            return  "가입이 가능한 아이디입니다";
        }

//        int result = loginInfoMapper.checkId(loginId);
//        return (result>0) ? "아이디 중복체크 성공": "중복체크 실패";
    }
}


