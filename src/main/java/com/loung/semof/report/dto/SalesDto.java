package com.loung.semof.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

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
