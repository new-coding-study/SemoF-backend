package com.loung.semof.loginInfo.dto;

import com.loung.semof.common.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDto implements UserDetails {
    private Long memberCode;
    private String memberId;
    private String memberPassword;
    private Integer empNo;
    private String empReg;
    private String memberRole;



////    public LoginInfoDto(Long empNo, String empName, String empReg, String email, String phone, String address, Integer salary, LocalDateTime enrollDate, LocalDateTime retireDate, String workStatus, String gender, Long jobCode, String deptCode, Long branchCode, Long memberCode) {
////        super(empNo, empName, empReg, email, phone, address, salary, enrollDate, retireDate, workStatus, gender, jobCode, deptCode, branchCode);
////        this.memberCode = memberCode;
////    }
//    public LoginInfoDto(String empReg, String memberRole) {
//        super(empReg, memberRole);
////        this.memberCode = memberCode;
//    }
    // 이하 코드는 security 를 위한 용도
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.memberPassword;
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "memberCode=" + memberCode +
                ", memberId='" + memberId + '\'' +
                ", memberPassword='" + memberPassword + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
