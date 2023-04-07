package com.loung.semof.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MeetingDto {

    private Integer rowNum;
    private Integer meetingReportCode;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate reportWriteDate;
    private Integer empNo;
    private String empName;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate meetingDate;
    private String location;
    private String participants;
    private String meetingReportTitle;
    private String meetingReportContent;
    private String conclusion;
    private String nextMeetingPlan;
    private String reportCategoryName;
    private char reportStatus;
    private String reportComment;
}
