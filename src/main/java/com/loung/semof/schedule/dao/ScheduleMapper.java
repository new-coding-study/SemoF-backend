package com.loung.semof.schedule.dao;

import com.loung.semof.schedule.dto.CalendarDto;
import com.loung.semof.schedule.dto.ScheduleCommentDto;
import com.loung.semof.schedule.dto.ScheduleDto;
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
public interface ScheduleMapper {

    // 스케줄 관련 Mapper
    List<ScheduleDto> selectScheduleList(Long empNo);
    ScheduleDto selectScheduleDetail(Long scdNo);
    int insertSchedule(ScheduleDto scheduleDto);
    int updateSchedule(ScheduleDto scheduleDto);
    int deleteSchedule(Long scdNo);
    List<ScheduleDto> selectSearchSchedule(String searchSchedule, String empNo);

    // 캘린더 관련 Mapper
     List<ScheduleDto> selectCalendarList(Long empNo);
    int insertCalendar(CalendarDto calendarDto);
    Long selectCalendarNo(CalendarDto calendarDto);
    int updateCalendar(CalendarDto calendarDto);
//    int updateCalendarMem(Long calNo, Long memNo);
    int deleteCalendarAllMem(Long calNo);

    // 캘린더 멤버 관련 Mapper
    List<CalendarDto> selectCalendarMemberList(Long calNo);
    int insertCalendarMem(Long memNo, Long calNo);
    int deleteCalendarOnlyOne(Long calNo, Long empNo);

    // 일정 댓글 관련 Mapper
    List<ScheduleCommentDto> selectScheduleCommentList(Long scdNo);
    int insertScheduleComment(ScheduleCommentDto scheduleCommentDto);
    int updateScheduleComment(ScheduleCommentDto scheduleCommentDto);
    int deleteScheduleComment(Long comNo);
}
