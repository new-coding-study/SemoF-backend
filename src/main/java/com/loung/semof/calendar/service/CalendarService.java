package com.loung.semof.calendar.service;

import com.loung.semof.humanresource.Exception.NotFoundException;
import org.springframework.stereotype.Service;
import com.loung.semof.calendar.dao.CalendarMapper;
import com.loung.semof.calendar.dto.CalendarDto;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CalendarService {

    private final CalendarMapper calendarMapper;

    public CalendarService(CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    public List<CalendarDto> selectScheduleList(Long empNo) {

        return calendarMapper.selectScheduleList(empNo);

    }

    public CalendarDto selectScheduleDetail(Long scheduleNo) throws NotFoundException{

        CalendarDto schedule = calendarMapper.selectScheduleDetail(scheduleNo);

        if (schedule == null) {
            throw new NotFoundException("할 일 상세보기 조회 실패");
        }
        return schedule;
    }

    public List<CalendarDto> selectSearchSchedule(String searchWord, String empNo) {

        System.out.println(searchWord);
        System.out.println(empNo);

        System.out.println(calendarMapper.selectSearchSchedule(searchWord, empNo));

        return calendarMapper.selectSearchSchedule(searchWord, empNo);

    }

    public List<CalendarDto> selectCategoryList(Long empNo) {

        List<CalendarDto> categoryList = calendarMapper.selectCategoryList(empNo);

        if (categoryList.isEmpty()) {
            throw new NotFoundException("카테고리 조회 실패");
        }

        return categoryList;
    }

    public String insertCategory(CalendarDto categoryDto) throws SQLException {

        int result = calendarMapper.insertCategory(categoryDto);

        if (result != 1) {
            throw new SQLException("카테고리 생성 실패");
        }

        return "카테고리 생성 성공";
    }

    public String updateCategory(CalendarDto categoryDto) throws SQLException {

        int result = calendarMapper.updateCategory(categoryDto);

        if (result != 1) {
            throw new SQLException("카테고리 수정 실패");
        }

        return "카테고리 수정 성공";
    }

    public String deleteCategory(Long cateNo) throws SQLException {

        int result = calendarMapper.deleteCategory(cateNo);

        if (result != 1) {
            throw new SQLException("카테고리 삭제 실패");
        }

        return "카테고리 삭제 성공";
    }

    public String insertSchedule(CalendarDto calendarDto) throws SQLException {

        System.out.println("Service 호출");

        if (calendarDto.getScdStartDate() == null || calendarDto.getScdStartDate().length() == 0) {

            LocalDateTime now = LocalDateTime.now();
            // 포맷 정의
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 포맷 적용
            calendarDto.setScdStartDate((now.format(formatter)));
        }

        if (calendarDto.getScdStartTime() == null || calendarDto.getScdStartTime().length() == 0) {
            calendarDto.setScdStartTime(("23:00:00"));
        }

        int result = calendarMapper.insertSchedule(calendarDto);

        if (result != 1) {
            throw new SQLException("할 일 생성 실패");
        }

        return "할 일 생성 성공";
    }

    public String updateSchedule(CalendarDto calendarDto) throws SQLException {

        int result = calendarMapper.updateSchedule(calendarDto);

        if (result != 1) {
            throw new SQLException("할 일 수정 실패");
        }

        return "할 일 수정 성공";
    }

    public String deleteSchedule(Long scheduleNo) throws SQLException {

        int result = calendarMapper.deleteSchedule(scheduleNo);

        if (result != 1) {
            throw new SQLException("할 일 삭제 실패");
        }

        return "할 일 삭제 성공";
    }

    public String updateStar(Long scheduleNo, Long changeStar) throws SQLException {

//        int scheduleStar = scheduleMapper.checkStar(scheduleNo);
//        System.out.println("scheduleStar" + scheduleStar);
//
//        Long changeStar = (long) (scheduleStar == 0 ? 1: 0);
//        System.out.println("changeStar" + changeStar);

        int result = calendarMapper.updateStar(scheduleNo, changeStar);

        if (result != 1) {
            throw new SQLException("중요 표시 변경 실패");

        }

        return "중요 표시 변경 성공";
    }

    public String updateFinish(Long scheduleNo, Long changeFinish) throws SQLException {

//        int scheduleFinish = scheduleMapper.checkFinish(scheduleNo);
//        System.out.println("scheduleFinish" + scheduleFinish);
//
//        Long changeFinish = (long) (scheduleFinish == 0 ? 1: 0);
//        System.out.println("changeFinish" + changeFinish);

        int result = calendarMapper.updateFinish(scheduleNo, changeFinish);

        if (result != 1) {
            throw new SQLException("완료 여부 표시 변경 실패");

        }

        return "완료 여부 표시 변경 성공";
    }


}
