package com.loung.semof.calendar.controller;

import com.loung.semof.calendar.dto.CalendarDto;
import com.loung.semof.calendar.service.CalendarService;
import com.loung.semof.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


/**
 * @파일이름 : CalendarController.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023/04/08
 * @작성자 : 박지희
 * @클래스설명 : 캘린더를 관리하는 컨트롤러
 */

@Slf4j
@RestController
@RequestMapping("/schedules")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    /**
     * @작성일 : 2023/03/21
     * @작성자 : 박지희
     * @메소드설명 : 로그인한 사원의 스케쥴을 전체 조회하는 메소드
     */
    @GetMapping("/schedulelist/{empNo}")
    public ResponseEntity<ResponseDto> selectScheduleList(@PathVariable Long empNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 전체 조회 성공", calendarService.selectScheduleList(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @GetMapping("/schedule/{scheduleNo}")
    public ResponseEntity<ResponseDto> selectscheduleDetail(@PathVariable Long scheduleNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 상세 조회 성공", calendarService.selectScheduleDetail(scheduleNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @GetMapping("/Schedule/search")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDto> selectSearchSchedule(@RequestParam(name="s") String searchWord, @RequestParam(name="e") String empNo){

        System.out.println(searchWord);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 검색 성공", calendarService.selectSearchSchedule(searchWord, empNo)));
//        try {
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
//
//        }
    }


    @GetMapping("/category/{empNo}")
    public ResponseEntity<ResponseDto> selectCategoryList(@PathVariable Long empNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "카테고리 조회 성공", calendarService.selectCategoryList(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PostMapping("/category")
    public ResponseEntity<ResponseDto> insertCategory(@ModelAttribute CalendarDto categoryDto){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "카테고리 생성 성공", calendarService.insertCategory(categoryDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PutMapping(value="/category")
    public ResponseEntity<ResponseDto> updateCategory(@ModelAttribute CalendarDto categoryDto){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "카테고리 수정 성공", calendarService.updateCategory(categoryDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @DeleteMapping(value="/category/{cateNo}")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable Long cateNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "카테고리 삭제 성공", calendarService.deleteCategory(cateNo)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PostMapping("/schedule")
    public ResponseEntity<ResponseDto> insertSchedule(@ModelAttribute CalendarDto calendarDto){

        try {
//            System.out.println("Controller 호출");
//            System.out.println("scheduleDto 확인 : " + scheduleDto);
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 추가 성공", calendarService.insertSchedule(calendarDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PutMapping(value="/schedule")
    public ResponseEntity<ResponseDto> updateSchedule(@ModelAttribute CalendarDto calendarDto){

        try {
            System.out.println("scheduleDto확인 : " + calendarDto);
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 수정 성공", calendarService.updateSchedule(calendarDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @DeleteMapping(value="/schedule/{scheduleNo}")
    public ResponseEntity<ResponseDto> deleteSchedule(@PathVariable Long scheduleNo) throws SQLException {

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 삭제 성공", calendarService.deleteSchedule(scheduleNo)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PutMapping(value="/star/{scheduleNo}")
    public ResponseEntity<ResponseDto> updateStar(@PathVariable Long scheduleNo, @RequestBody Long changeStar){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 표시 변경 성공", calendarService.updateStar(scheduleNo, changeStar)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PutMapping(value="/finish/{scheduleNo}")
    public ResponseEntity<ResponseDto> updateFinish(@PathVariable Long scheduleNo, @RequestBody Long changeFinish){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 표시 변경 성공", calendarService.updateFinish(scheduleNo, changeFinish)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

}
