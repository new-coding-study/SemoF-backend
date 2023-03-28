package com.loung.semof.attendance.controller;

import com.loung.semof.attendance.service.AttendanceService;
import com.loung.semof.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/attendance")
public class AttendanceController {


    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }


    /**
     * @작성일 : 2023-03-27 027
     * @작성자 : sik
     * @메소드설명 : 사원 근태정보 상세 조회
     */
    @GetMapping("/status/{empNo}")
    public ResponseEntity<ResponseDto> selectAttendanceDetail(@PathVariable (name = "empNo") int empNo) {
        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원 근태정보 조회 성공", attendanceService.selectAttendanceDetail(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
        // return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원 근태정보 조회 성공", attendanceService.selectAttendanceDetail(empNo)));
    }

    /**
     * @작성일 : 2023-03-27 027
     * @작성자 : sik
     * @메소드설명 : 사원 근태기록 조회
     */
    @GetMapping("/status/histories/{empNo}")
    public ResponseEntity<ResponseDto> selectAttendanceList(@PathVariable (name = "empNo") int empNo) {
        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원 근태기록 조회 성공", attendanceService.selectAttendanceList(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

    /**
     * @작성일 : 2023-03-28 027
     * @작성자 : sik
     * @메소드설명 : 사원 연차 현황 상세 조회
     */
    @GetMapping("/annual/{empNo}")
    public ResponseEntity<ResponseDto> selectVacationDetail(@PathVariable (name = "empNo") int empNo) {
        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원 연차 현황 조회 성공", attendanceService.selectVacationDetail(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

    /**
     * @작성일 : 2023-03-28 027
     * @작성자 : sik
     * @메소드설명 : 사원 근태 상태 변경
     */
    @PutMapping("/status/{empNo}")
    public ResponseEntity<ResponseDto> updateAttendance(@PathVariable (name = "empNo") int empNo, int statusCode) {
        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원 근태 상태 변경 성공", attendanceService.updateAttendance(empNo, statusCode)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }





    /* 총 갯수 구해서 페이징 처리한 스티커 전체 조회 */
    /* @GetMapping("/attendances")
    public ResponseEntity<ResponseDto> selectAttendanceListWithPaging(@RequestParam(name="offset", defaultValue="1") String offset) {

        log.info("[AttendanceController] selectAttendanceListWithPaging : " + offset);

        int totalCount = attendanceService.selectAttendanceTotal();   //총 갯수 구하기
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        log.info("[AttendanceController] selectCriteria : " + selectCriteria);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(attendanceService.selectAttendanceListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 조회 성공", responseDtoWithPaging));
    } */

    /* 스티커 상세 조회 */
    /* @GetMapping("/attendances/{attendanceCode}")
    public ResponseEntity<ResponseDto> selectAttendanceDetail(@PathVariable String attendanceCode) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "스티커 상세정보 조회 성공",  attendanceService.selectAttendance(attendanceCode)));
    } */

    /* 카테고리별 리스트 조회 */
    /* @GetMapping("/attendances/categories/{categoryCode}")
    public ResponseEntity<ResponseDto> selectAttendanceListAboutCategory(@PathVariable String categoryCode) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "카테고리별 스티커 조회 성공",  attendanceService.selectAttendanceListAboutCategory(categoryCode)));
    } */

    /* 타입별 리스트 조회 */
    /* @GetMapping("/attendances/types/{typeCode}")
    public ResponseEntity<ResponseDto> selectAttendanceListAboutType(@PathVariable String typeCode) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "타입별 스티커 조회 성공",  attendanceService.selectAttendanceListAboutType(typeCode)));
    } */

    /* 스티커 전체조회 (관리자) */
    /* @GetMapping("/attendances-management")
    public ResponseEntity<ResponseDto> selectAttendanceListWithPagingForAdmin(@RequestParam(name="offset", defaultValue="1") String offset) {

        log.info("[AttendanceController] selectAttendanceListWithPagingForAdmin : " + offset);

        int totalCount = attendanceService.selectAttendanceTotal();
        int limit = 10;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);;

        log.info("[AttendanceController] selectCriteria : " + selectCriteria);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(attendanceService.selectAttendanceListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    } */

    /* 스티커 상세조회 (관리자) */
    /* @GetMapping("/attendances-management/{attendanceCode}")
    public ResponseEntity<ResponseDto> selectAttendanceDetailForAdmin(@PathVariable String attendanceCode) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "상품 상세정보 조회 성공", attendanceService.selectAttendance(attendanceCode)));
    } */

    /* 스티커 등록 (관리자) */
    /* @PostMapping(value = "/attendances-management")
    public ResponseEntity<ResponseDto> insertAttendance(@ModelAttribute AttendanceDto attendanceDto) {
        log.info("[AttendanceController] PostMapping attendanceDto : " + attendanceDto);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "스티커 등록 성공",  attendanceService.insertAttendance(attendanceDto)));
    } */

    /* 스티커 수정 (관리자) */
    /* @PutMapping(value = "/attendances-management/{attendanceNo}")
    public ResponseEntity<ResponseDto> updateAttendance(@ModelAttribute AttendanceDto attendanceDto, @PathVariable String attendanceNo) {
        log.info("[AttendanceController]PutMapping attendanceNo : " + attendanceNo);
        log.info("[AttendanceController]PutMapping attendanceDto : " + attendanceDto);

        attendanceDto.setAttendanceCode(Integer.parseInt(attendanceNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "스티커 수정 성공",  attendanceService.updateAttendance(attendanceDto)));
    } */

    /* 스티커 삭제 */
    /* @DeleteMapping("/attendances-management/{attendanceNo}")
    public ResponseEntity<ResponseDto> deleteAttendance(@PathVariable String attendanceNo) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "스티커 삭제 성공",  attendanceService.deleteAttendance(attendanceNo)));
    } */

    /* 스티커 검색 */
    /* @GetMapping("/attendances/search")
    public ResponseEntity<ResponseDto> selectSearchList(@RequestParam(name="condition", defaultValue = "attendanceName") String condition, @RequestParam(name="searchValue", required = false) String searchValue ) {

        HashMap<String, String> search = new HashMap<>();
        search.put("condition", condition);
        search.put("searchValue", searchValue );

        System.out.println(condition);
        System.out.println(searchValue);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공",  attendanceService.selectSearchAttendanceList(search)));
    } */

}
