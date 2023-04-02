package com.loung.semof.humanresource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loung.semof.common.dto.DepartmentDto;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @파일이름 : DepartmentOrderDto.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-31
 * @작성자 : 이현도
 * @클래스설명 : 모델(Model)과 뷰(View) 사이에서 데이터 전송을 담당하는 클래스
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentOrderDto {
    
    private Long deptOrderNo;   // 부서 발령번호
    private Long empNo; // 사원번호
    private String deptCode; //부서코드
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime orderDate; //발령일
    private DepartmentDto departmentDto;
    private String newDeptCode;   //  새로운 부서 코드
}
