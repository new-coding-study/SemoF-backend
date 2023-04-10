package com.loung.semof.schedule.controller;

import com.loung.semof.schedule.dto.CalendarDto;
import com.loung.semof.schedule.dto.ScheduleCommentDto;
import com.loung.semof.schedule.dto.ScheduleDto;
import com.loung.semof.schedule.service.ScheduleService;
import com.loung.semof.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


/**
 * @파일이름 : CalendarController.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023/04/08
 * @작성자 : 박지희
 * @클래스설명 : 일정과 캘린더를 관리하는 컨트롤러
 */

@Slf4j
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 일정을 전체 조회하는 메소드
     */
    @GetMapping("/schedulelist/{empNo}")
    public ResponseEntity<ResponseDto> selectScheduleList(@PathVariable Long empNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 전체 조회 성공", scheduleService.selectScheduleList(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 선택한 일정을 상세 조회하는 메소드
     */
    @GetMapping("/schedule/{scdNo}")
    public ResponseEntity<ResponseDto> selectScheduleDetail(@PathVariable Long scdNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 상세 조회 성공", scheduleService.selectScheduleDetail(scdNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 새로운 일정을 추가하는 메소드
     */
    @PostMapping("/schedule")
    public ResponseEntity<ResponseDto> insertSchedule(@ModelAttribute ScheduleDto scheduleDto){

        try {
//            System.out.println("Controller 호출");
//            System.out.println("scheduleDto 확인 : " + scheduleDto);
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 추가 성공", scheduleService.insertSchedule(scheduleDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 일정을 수정하는 메소드
     */
    @PutMapping(value="/schedule")
    public ResponseEntity<ResponseDto> updateSchedule(@ModelAttribute ScheduleDto scheduleDto){

        try {
            System.out.println("scheduleDto확인 : " + scheduleDto);
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 수정 성공", scheduleService.updateSchedule(scheduleDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 일정을 삭제하는 메소드
     */
    @DeleteMapping(value="/schedule/{scdNo}")
    public ResponseEntity<ResponseDto> deleteSchedule(@PathVariable Long scdNo) {

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 삭제 성공", scheduleService.deleteSchedule(scdNo)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 검색어를 포함하는 일정을 조회하는 메소드
     */
    @GetMapping("/schedule/search")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDto> selectSearchSchedule(@RequestParam(name="s") String searchSchedule, @RequestParam(name="e") String empNo){

        System.out.println(searchSchedule);


        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 검색 성공", scheduleService.selectSearchSchedule(searchSchedule, empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }
    
    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 캘린더를 전체 조회하는 메소드
     */
    @GetMapping("/calendar/{empNo}")
    public ResponseEntity<ResponseDto> selectCalendarList(@PathVariable Long empNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "캘린더 조회 성공", scheduleService.selectCalendarList(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 새로운 캘린더를 추가하는 메소드
     */
    @PostMapping("/calendar")
    public ResponseEntity<ResponseDto> insertCalendar(@ModelAttribute CalendarDto calendarDto){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "캘린더 생성 성공", scheduleService.insertCalendar(calendarDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 캘린더에 대한 정보를 수정하는 메소드
     */
    @PutMapping(value="/calendar")
    public ResponseEntity<ResponseDto> updateCalendar(@ModelAttribute CalendarDto calendarDto, @ModelAttribute List<Long> calendarMemNoList){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "캘린더 수정 성공", scheduleService.updateCalendar(calendarDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 캘린더를 삭제하는 메소드
     */
    @DeleteMapping(value="/calendar/{calNo}")
    public ResponseEntity<ResponseDto> deleteCalendar(@PathVariable Long calNo) {

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "캘린더 삭제 성공", scheduleService.deleteCalendar(calNo)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    // 캘린더에 그룹원 추가하는 메소드 만들어야함 => Service 단에서 캘린더 생성하는 메소드 안에서 하고 있음
    // 그룹원 삭제 메소드 만들어야함 => 캘린더 수정과 캘린더멤버 수정을 각각 발생시키기 위함

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 캘린더 멤버를 전체 조회하는 메소드
     */
    @GetMapping("/calendar/member/{calNo}")
    public ResponseEntity<ResponseDto> selectCalendarMemberList(@PathVariable Long calNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "캘린더 조회 성공", scheduleService.selectCalendarMemberList(calNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 캘린더에 멤버를 추가하는 메소드
     */
    @PostMapping("/calendar/member/{calNo}")
    public ResponseEntity<ResponseDto> insertCalendarMember(@PathVariable Long calNo, @ModelAttribute List<Long> calMemList){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "캘린더 생성 성공", scheduleService.insertCalendarMember(calNo, calMemList)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 캘린더 멤버 한 명을 삭제하는 메소드
     */
    @DeleteMapping(value="/calendar/member/{calNo}/{empNo}")
    public ResponseEntity<ResponseDto> deleteCalendarOnlyOne(@PathVariable(value = "calNo") Long calNo, @PathVariable(value = "empNo") Long empNo) {

        System.out.println("empNo : " + empNo);
        System.out.println("calNo : " + calNo);

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "캘린더 삭제 성공", scheduleService.deleteCalendarOnlyOne(calNo, empNo)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 일정에 대한 댓글을 전체 조회하는 메소드
     */
    @GetMapping("/schedule/comment/{scdNo}")
    public ResponseEntity<ResponseDto> selectScheduleCommentList(@PathVariable Long scdNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 댓글 조회 성공", scheduleService.selectScheduleCommentList(scdNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 새로운 댓글 추가하는 메소드
     */
    @PostMapping("/schedule/comment")
    public ResponseEntity<ResponseDto> insertScheduleComment(@ModelAttribute ScheduleCommentDto scheduleCommentDto){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 댓글 생성 성공", scheduleService.insertScheduleComment(scheduleCommentDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 댓글에 대한 정보를 수정하는 메소드
     */
    @PutMapping(value="/schedule/comment")
    public ResponseEntity<ResponseDto> updateScheduleComment(@ModelAttribute ScheduleCommentDto scheduleCommentDto){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "일정 댓글 수정 성공", scheduleService.updateScheduleComment(scheduleCommentDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    /**
     * @작성일 : 2023/04/08
     * @작성자 : 박지희
     * @메소드설명 : 댓글 삭제하는 메소드
     */
    @DeleteMapping(value="/schedule/comment/{comNo}")
    public ResponseEntity<ResponseDto> deleteScheduleComment(@PathVariable Long comNo) {

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "댓글 삭제 성공", scheduleService.deleteScheduleComment(comNo)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

//    @PutMapping(value="/star/{scheduleNo}")
//    public ResponseEntity<ResponseDto> updateStar(@PathVariable Long scheduleNo, @RequestBody Long changeStar){
//
//        try {
//            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 표시 변경 성공", calendarService.updateStar(scheduleNo, changeStar)));
//
//        } catch (SQLException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
//
//        }
//    }

//    @PutMapping(value="/finish/{scheduleNo}")
//    public ResponseEntity<ResponseDto> updateFinish(@PathVariable Long scheduleNo, @RequestBody Long changeFinish){
//
//        try {
//            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 표시 변경 성공", calendarService.updateFinish(scheduleNo, changeFinish)));
//
//        } catch (SQLException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
//
//        }
//    }

}
