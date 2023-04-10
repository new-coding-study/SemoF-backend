package com.loung.semof.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 파일이름 : ScheduleDto
 *
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023/04/09
 * @작성자 : 박지희
 * @클래스설명 : 설명을 여기에 작성한다.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    private Long scdNo;
    private String scdName;
    private String scdStartDay;
    private String scdStartTime;
    private String scdEndDay;
    private String scdEndTime;
    private int scdAllDay;
    private String scdContent;
    private String scdPlace;
    private Long scdWriter;
    private String scdWriterName;
    private CalendarDto calendar;

    // 캘린더 테이블
    private Long calNo; // 외래키
    private String calName;
    private String calColor;
    private Long madeEmpNo;
    private String madeEmpName;
}
