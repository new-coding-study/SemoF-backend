package com.loung.semof.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @파일이름 : TripDto.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이지형
 * @클래스설명 : 출장보고서 crud를 위한 dto.
 */
@Data
public class TripDto {

    private Integer rowNum;
    private Integer tripReportCode;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate reportWriteDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate startDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate endDate;
    private Integer empNo;
    private String empName;
    private String destination;
    private String tripReportTitle;
    private String tripReportContent;
    private String issuesImprovement;
    private String conclusion;
    private String reportCategoryName;
    private char reportStatus;
    private String reportComment;
}
