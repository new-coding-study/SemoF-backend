package com.loung.semof.schedule.service;

import com.loung.semof.humanresource.Exception.NotFoundException;
import com.loung.semof.schedule.dto.CalendarDto;
import com.loung.semof.schedule.dto.ScheduleCommentDto;
import org.springframework.stereotype.Service;
import com.loung.semof.schedule.dao.ScheduleMapper;
import com.loung.semof.schedule.dto.ScheduleDto;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.SQLException;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;


    public ScheduleService(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 일정을 전체 조회하는 메소드
     */
    public List<ScheduleDto> selectScheduleList(Long empNo) {

        return scheduleMapper.selectScheduleList(empNo);

    }

    public ScheduleDto selectScheduleDetail(Long scdNo) throws NotFoundException{

        ScheduleDto schedule = scheduleMapper.selectScheduleDetail(scdNo);

        if (schedule == null) {
            throw new NotFoundException("일정 상세 조회 실패");
        }
        return schedule;
    }

    public String insertSchedule(ScheduleDto scheduleDto) throws SQLException {

//        System.out.println("Service 호출");
//
//        if (scheduleDto.getScdStartDay() == null || scheduleDto.getScdStartDay().length() == 0) {
//
//            LocalDateTime now = LocalDateTime.now();
//            // 포맷 정의
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            // 포맷 적용
//            scheduleDto.setScdStartDay((now.format(formatter)));
//        }
//
//        if (scheduleDto.getScdStartTime() == null || scheduleDto.getScdStartTime().length() == 0) {
//            scheduleDto.setScdStartTime(("23:00:00"));
//        }

        int result = scheduleMapper.insertSchedule(scheduleDto);

        if (result != 1) {
            throw new SQLException("일정 생성 실패");
        }

        return "일정 생성 성공";
    }

    public String updateSchedule(ScheduleDto scheduleDto) throws SQLException {

        int result = scheduleMapper.updateSchedule(scheduleDto);

        if (result != 1) {
            throw new SQLException("일정 수정 실패");
        }

        return "일정 수정 성공";
    }

    public String deleteSchedule(Long scdNo) throws SQLException {

        int result = scheduleMapper.deleteSchedule(scdNo);

        if (result != 1) {
            throw new SQLException("일정 삭제 실패");
        }

        return "일정 삭제 성공";
    }

    public List<ScheduleDto> selectSearchSchedule(String searchSchedule, String empNo) {

        System.out.println("service" + searchSchedule);
        System.out.println("service" + empNo);

        System.out.println(scheduleMapper.selectSearchSchedule(searchSchedule, empNo));

        return scheduleMapper.selectSearchSchedule(searchSchedule, empNo);

    }

    public List<ScheduleDto> selectCalendarList(Long empNo) {

        List<ScheduleDto> calendarList = scheduleMapper.selectCalendarList(empNo);

        if (calendarList.isEmpty()) {
            throw new NotFoundException("캘린더(일정 그룹) 조회 실패");
        }

        return calendarList;
    }

    public CalendarDto selectCalendarDetail(Long calNo) {

        CalendarDto calendar = scheduleMapper.selectCalendarDetail(calNo);

        if (calendar == null) {
            throw new NotFoundException("캘린더(일정 그룹) 조회 실패");
        }

        return calendar;
    }

    public String insertCalendar(CalendarDto calendarDto) throws SQLException {

        int insertCalendarResult = scheduleMapper.insertCalendar(calendarDto);

        // 새롭게 생성한 캘린더의 No을 구함
        Long calNo = scheduleMapper.selectCalendarNo(calendarDto);
        System.out.println("방금 입력한 캘린더의 No는? " + calNo);

        // 캘린더를 만든 사람도 캘린더멤버 테이블에 값을 넣어줘야 해서 아래 코드 실행
        // => 이게 싫으면 where 절에서 or 조건으로 made_emp_no 와 emp_no 로 찾으면 될 듯
        // => or 조건을 쓰면 made_emp_no 을 조회할 때 여러개가 나와벌임,,, => distinct 로 해결?!
        // => 일정 조회할 때도 or 조건으로 걸어서 가져와야함..
        // 어떤게 좋을지 고민해보기

        // or 조건 문제점 : tbl_calendar_mem 에 존재하지 않으면 가져오지 못함 => 개인적인 캘린더를 하나도 못 가져옴
        // => 그냥 아래 코드 실행해서 맘편히 넣어주자!!!!
        // => 아니면 Left Join...?
        int insertCalendarMemResult = scheduleMapper.insertCalendarMem(calendarDto.getMadeEmpNo(), calNo);
        System.out.println("insertCalendarMemResult : " + insertCalendarMemResult);

//        int calendarMemInsertResult = 0;
//
//        if(!calendarMemNoList.isEmpty()) {
//            for ( Long memNo : calendarMemNoList)
//            { calendarMemInsertResult += scheduleMapper.insertCalendarMem(memNo, calNo); }
//        } else {
//            System.out.println("리스트가 비어있음");
//        }

        if (insertCalendarResult != 1 || insertCalendarMemResult != 1) {
            throw new SQLException("캘린더(일정 그룹) 생성 실패");
        }

        return "캘린더(일정 그룹) 생성 성공";
    }

    public String updateCalendar(CalendarDto calendarDto) throws SQLException {

        int calendarUpdateResult = scheduleMapper.updateCalendar(calendarDto);

        if (calendarUpdateResult != 1) {
            throw new SQLException("캘린더(일정 그룹) 수정 실패");
        }

        return "캘린더(일정 그룹) 수정 성공";
    }

    public String deleteCalendar(Long calNo) throws SQLException {

        int result;

        result = scheduleMapper.deleteCalendarAllMem(calNo);

        if (result != 1) {
            throw new SQLException("캘린더(일정 그룹) 삭제 실패");
        }

        return "캘린더(일정 그룹) 삭제 성공";
    }

    // 캘린더 멤버 관련 Service
    public List<CalendarDto> selectCalendarMemberList(Long calNo) {

        return scheduleMapper.selectCalendarMemberList(calNo);
    }

    public String insertCalendarMember(Long calNo, List<Long> calMemList) throws SQLException {

        int result = 0;

        for (Long calMemNo: calMemList) {
            result += scheduleMapper.insertCalendarMem(calNo, calMemNo);
        }

        if (result != calMemList.size()) {
            throw new SQLException("캘린더 그룹원 추가 실패");
        }

        return "캘린더 그룹원 추가 성공";
    }

    public String deleteCalendarOnlyOne(Long calNo, Long empNo) throws SQLException {

        int result;

        result = scheduleMapper.deleteCalendarOnlyOne(calNo, empNo);


        if (result != 1) {
            throw new SQLException("캘린더(일정 그룹) 삭제 실패");
        }

        return "캘린더(일정 그룹) 삭제 성공";
    }



    // 일정 댓글 관련 Service
    public List<ScheduleCommentDto> selectScheduleCommentList(Long scdNo) {

        return scheduleMapper.selectScheduleCommentList(scdNo);
    }

    public String insertScheduleComment(ScheduleCommentDto scheduleCommentDto) throws SQLException {

        int result = scheduleMapper.insertScheduleComment(scheduleCommentDto);

        if (result != 1) {
            throw new SQLException("댓글 생성 실패");
        }

        return "댓글 생성 성공";
    }

    public String updateScheduleComment(ScheduleCommentDto scheduleCommentDto) throws SQLException {

        int result = scheduleMapper.updateScheduleComment(scheduleCommentDto);

        if (result != 1) {
            throw new SQLException("댓글 수정 실패");
        }

        return "댓글 수정 성공";
    }

    public String deleteScheduleComment(Long comNo) throws SQLException {

        int result = scheduleMapper.deleteScheduleComment(comNo);

        if (result != 1) {
            throw new SQLException("댓글 삭제 실패");
        }

        return "댓글 삭제 성공";
    }

//    public String updateStar(Long scheduleNo, Long changeStar) throws SQLException {
//
////        int scheduleStar = scheduleMapper.checkStar(scheduleNo);
////        System.out.println("scheduleStar" + scheduleStar);
////
////        Long changeStar = (long) (scheduleStar == 0 ? 1: 0);
////        System.out.println("changeStar" + changeStar);
//
//        int result = calendarMapper.updateStar(scheduleNo, changeStar);
//
//        if (result != 1) {
//            throw new SQLException("중요 표시 변경 실패");
//
//        }
//
//        return "중요 표시 변경 성공";
//    }
//
//    public String updateFinish(Long scheduleNo, Long changeFinish) throws SQLException {
//
////        int scheduleFinish = scheduleMapper.checkFinish(scheduleNo);
////        System.out.println("scheduleFinish" + scheduleFinish);
////
////        Long changeFinish = (long) (scheduleFinish == 0 ? 1: 0);
////        System.out.println("changeFinish" + changeFinish);
//
//        int result = calendarMapper.updateFinish(scheduleNo, changeFinish);
//
//        if (result != 1) {
//            throw new SQLException("완료 여부 표시 변경 실패");
//
//        }
//
//        return "완료 여부 표시 변경 성공";
//    }


}
