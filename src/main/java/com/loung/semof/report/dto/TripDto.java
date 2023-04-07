package com.loung.semof.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

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
