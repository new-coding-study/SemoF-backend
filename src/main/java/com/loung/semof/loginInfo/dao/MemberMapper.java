package com.loung.semof.loginInfo.dao;


import com.loung.semof.loginInfo.dto.LoginInfoDto;
import com.loung.semof.loginInfo.dto.LoginInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;


@Mapper
public interface MemberMapper {

    LoginInfoDto selectByEmail(String email);

    int insertMember(LoginInfoDto member);

    Optional<LoginInfoDto> findByMemberId(String memberId);

    LoginInfoDto selectByMemberId(String memberId);

    LoginInfoDto selectByMemberReg(String empReg);
}
