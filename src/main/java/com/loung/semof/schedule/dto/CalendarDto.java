package com.loung.semof.schedule.dto;

import com.loung.semof.common.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDto {

    // 캘린더 테이블
    private Long calNo; // 외래키
    private String calName;
    private String calColor;
    private Long madeEmpNo;
    private String madeEmpName;
    private List<EmployeeDto> calendarMem;
}
