package com.loung.semof.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDto {
    private Long scdNo;
    private String scdName;
    private String scdStartDate;
    private String scdStartTime;
    private String scdEndDate;
    private String scdEndTime;
    private int scdAllDay;
    private String scdContent;
    private String scdPlace;

    // 캘린더 테이블
    private Long calNo; // 외래키
    private String calName;
    private String calColor;
    private Long empNo;
}
