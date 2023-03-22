package com.loung.semof.humanresource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @파일이름 : HumanResourceDto.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : 모델(Model)과 뷰(View) 사이에서 데이터 전송을 담당하는 클래스
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanResourceDto {

    private String empName; //사원이름
    private String empReg;  //사원주민등록번호
    private String email;   //사원이메일
    private String phone;   //사원휴대번호
    private String address; //사원주소
    private Integer salary; //사원급여
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime enrollDate;   //입사일
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime retireDate;   //퇴사일
    private String workStatus;  //재직상태
    private String gender;  //성별
    private Long jobCode;   //직급
    private String deptCode;    //부서
    private Long branchCode; //지점
    private Long empNo; // 사원번호
    private String jobName; // 직급명
    private String deptName;    // 부서명
    private String branchName;  //지점명

}
