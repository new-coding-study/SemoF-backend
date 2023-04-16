package com.loung.semof.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * @파일이름 : SalesDto.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이지형
 * @클래스설명 : 영업보고서 crud를 위한 dto.
 */
@Data
@AllArgsConstructor
public class SalesDto {

    private Integer rowNum;
    private Integer salesReportCode;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate reportWriteDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate startDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate endDate;
    private Integer empNo;
    private String empName;
    private String salesReportTitle;
    private String salesReportContent;
    private String customerComment;
    private String competitionAnalysis;
    private String issuesImprovement;
    private String nextPlan;
    private String conclusion;
    private String reportCategoryName;
    private char reportStatus;
    private String reportComment;
}
