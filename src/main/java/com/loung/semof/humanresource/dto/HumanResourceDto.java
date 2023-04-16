
package com.loung.semof.humanresource.dto;

import com.loung.semof.common.dto.EmployeeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @파일이름 : HumanResourceDto.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : 모델(Model)과 뷰(View) 사이에서 데이터 전송을 담당하는 클래스
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
public class HumanResourceDto extends EmployeeDto {

    public HumanResourceDto(Long empNo, String empName, String empReg, String email, String phone, String address, Integer salary, LocalDateTime enrollDate, LocalDateTime retireDate, String workStatus, String gender, Long jobCode, String deptCode, Long branchCode, String jobName, String deptName, String branchName, String memberRole) {
        super(empNo, empName, empReg, email, phone, address, salary, enrollDate, retireDate, workStatus, gender, jobCode, deptCode, branchCode, memberRole);
        this.jobName = jobName;
        this.deptName = deptName;
        this.branchName = branchName;
    }

    public HumanResourceDto(String jobName, String deptName, String branchName) {
        this.jobName = jobName;
        this.deptName = deptName;
        this.branchName = branchName;
    }

    /*인사 관리 관련*/
    private String jobName; // 직급명
    private String deptName;    // 부서명
    private String branchName;  //지점명
}
