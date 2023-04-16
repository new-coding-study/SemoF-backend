package com.loung.semof.humanresource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @파일이름 : EmployeeEvaluationDto.java
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
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeEvaluationDto {
    /*인사 평가 관련 */
    private Long atdNo; //근무번호
    private Long empNo; //사원번호
    private String empName; //사원이름
    private String gender; //성별
    private String jobName; //직무명
    private String deptName; //부서명
    private String branchName; //지점명
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime startTime; //출근시간
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime endTime; //퇴근시간
    private Long asNo;  // 상태번호
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime occurTime; //발생시간
    private Long statusCode; //상태코드
    private String statusName; // 상태명
    private Long workingDays;   //출근일수
    

    /* 프로젝트 기여도 관련 */
    private Long contNo; // 기여도번호
    private Long evalContNo;    // 기여도평가번호
    private Long evalAtdNo; //근태평가번호
    private Long categoryNo; //카테고리번호
    private String grade; //등급
    private Long projAContribution;   //프로젝트A 기여도
    private Long projBContribution; //프로젝트B 기여도
    private Long projCContribution; //프로젝트C 기여도
    private Long projTotalContribution; // 프로젝트 총 기여도
    private Double projAverageContribution; //프로젝트 전체 기여도 평균
}
