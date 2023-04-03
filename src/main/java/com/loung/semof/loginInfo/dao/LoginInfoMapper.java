package com.loung.semof.loginInfo.dao;


import com.loung.semof.loginInfo.dto.LoginInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;


@Mapper
public interface LoginInfoMapper {

    LoginInfoDto selectByEmail(String email);

    int insertMember(LoginInfoDto member);

    Optional<LoginInfoDto> findByMemberId(String memberId);

    LoginInfoDto selectByMemberId(String memberId);

    LoginInfoDto selectByEmpReg(String empReg);

    int checkEmpReg(String empReg);

   String selectById(String loginId);

    int checkId(String loginId);
}
