package com.loung.semof.calendar.dao;

import com.loung.semof.calendar.dto.CalendarDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일이름 : TodoMapper.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023/03/21
 * @작성자 : 박지희
 * @클래스설명 :
 */

@Mapper
public interface CalendarMapper {

    List<CalendarDto> selectScheduleList(Long empNo);

    CalendarDto selectScheduleDetail(Long scheduleNo);
    List<CalendarDto> selectSearchSchedule(String searchWord, String empNo);
    List<CalendarDto> selectCategoryList(Long empNo);
    int insertCategory(CalendarDto categoryDto);
    int updateCategory(CalendarDto categoryDto);
    int deleteCategory(Long cateNo);
    int insertSchedule(CalendarDto calendarDto);
    int updateSchedule(CalendarDto calendarDto);
    int deleteSchedule(Long scheduleNo);
    int checkStar(Long scheduleNo);
    int updateStar(Long scheduleNo, Long changeStar);
    int checkFinish(Long scheduleNo);

    int updateFinish(Long scheduleNo, Long changeFinish);

}
