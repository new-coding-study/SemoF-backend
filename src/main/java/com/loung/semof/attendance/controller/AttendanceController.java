package com.loung.semof.attendance.controller;

import com.loung.semof.attendance.service.AttendanceService;
import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/attendance")
public class AttendanceController {


    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /* 총 갯수 구해서 페이징 처리한 근태 기록 전체 조회 */
    @GetMapping("/status/histories/{empNo}")
    public ResponseEntity<ResponseDto> selectAttendanceListWithPaging(@RequestParam(name="offset", defaultValue="1") String offset, @PathVariable (name = "empNo") int empNo) {
        try {
            log.info("[AttendanceController] selectAttendanceListWithPaging : " + offset);

            int totalCount = attendanceService.selectAttendanceTotal(empNo);   //총 갯수 구하기
            int limit = 10;
            int buttonAmount = 5;

            SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

            log.info("[AttendanceController] selectCriteria : " + selectCriteria);

            ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
            responseDtoWithPaging.setPageInfo(selectCriteria);
            responseDtoWithPaging.setData(attendanceService.selectAttendanceListWithPaging(selectCriteria.getStartRow(), empNo));

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 조회 성공", responseDtoWithPaging));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
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
    }

    /**
     * @작성일 : 2023-03-27 027
     * @작성자 : sik
     * @메소드설명 : 사원 근태기록 조회
     */
    // @GetMapping("/status/histories/{empNo}")
    // public ResponseEntity<ResponseDto> selectAttendanceList(@PathVariable (name = "empNo") int empNo) {
    //     try {
    //         return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원 근태기록 조회 성공", attendanceService.selectAttendanceList(empNo)));
    //
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
    //     }
    // }

    /**
     * @작성일 : 2023-03-28 027
     * @작성자 : sik
     * @메소드설명 : 사원 근태 상태 변경
     */
    @PutMapping("/status/{empNo}")
    public ResponseEntity<ResponseDto> updateAttendance(@PathVariable (name = "empNo") int empNo, @RequestBody String nowTime ) {
        try {

            HashMap<String, String> data = new HashMap<>();
            data.put("empNo", String.valueOf(empNo));
            data.put("nowTime", nowTime);

            System.out.println(data.get("empNo"));
            System.out.println(data.get("nowTime"));

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원 근태 상태 변경 성공", attendanceService.updateAttendance(data)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

}
