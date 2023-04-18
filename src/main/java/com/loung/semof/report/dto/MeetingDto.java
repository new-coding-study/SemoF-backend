package com.loung.semof.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * @파일이름 : MeetingDto.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이지형
 * @클래스설명 : 회의보고서 crud를 위한 dto.
 */
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
