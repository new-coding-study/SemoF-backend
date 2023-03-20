package com.loung.semof.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private long empNo; //사원번호
    private String empName; //사원이름
    private String empReg;  //사원주민등록번호
    private String email;   //사원이메일
    private String phone;   //사원휴대번호
    private String address; //사원주소
    private int salary; //사원급여
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime enrollDate;   //입사일
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime retireDate;   //퇴사일
    private String workStatus;  //재직상태
    private String gender;  //성별
    private long jobCode;   //직급
    private String deptCode;    //부서
    private long bCode; //지점


}
