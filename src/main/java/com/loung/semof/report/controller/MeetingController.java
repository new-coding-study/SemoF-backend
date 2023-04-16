package com.loung.semof.report.controller;

import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.MeetingDto;
import com.loung.semof.report.service.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @파일이름 : MeetingController.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이지형
 * @클래스설명 : 회의보고서 crud 위한 controller.
 */
@RestController
@RequestMapping("/reports")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 회의보고서의 전체 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/meeting-lists-admin")
    public ResponseEntity<ResponseDto> selectAllMeetingReportForAdminWithPaging(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = meetingService.selectMeetingReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(meetingService.selectAllMeetingReportForAdminWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 회의보고서의 읽지 않은 전체 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/meeting-n-lists-admin")
    public ResponseEntity<ResponseDto> selectAllMeetingNStatusForAdmin(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = meetingService.selectMeetingReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(meetingService.selectAllMeetingNStatusForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 회의보고서의 읽은 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/meeting-y-lists-admin")
    public ResponseEntity<ResponseDto> selectAllMeetingYStatusForAdmin(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = meetingService.selectMeetingReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(meetingService.selectAllMeetingYStatusForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 회의보고서의 전체 리스트 조회 및 페이징
     */
    @GetMapping("/meeting-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllMeetingReportForEmpWithPaging(@RequestParam(name="offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = meetingService.selectMeetingReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(meetingService.selectAllMeetingReportForEmpWithPaging(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 회의보고서의 읽지 않은 리스트 조회 및 페이징
     */
    @GetMapping("/meeting-n-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllMeetingNStatusForEmp(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = meetingService.selectMeetingReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(meetingService.selectAllMeetingNStatusForEmp(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 회의보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 회의보고서의 읽은 리스트 조회 및 페이징
     */
    @GetMapping("/meeting-y-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllMeetingYStatusForEmp(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = meetingService.selectMeetingReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(meetingService.selectAllMeetingYStatusForEmp(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 회의보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 작성한 보고서에 대한 상세조회(관리자)
     */
    @GetMapping("/meeting-detail-admin/{meetingReportCode}")
    public ResponseEntity<ResponseDto> detailMeetingReportForAdmin(@PathVariable Integer meetingReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "관리자 회의보고서 조회", meetingService.selectDetailMeetingForAdmin(meetingReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 작성한 보고서에 대한 상세조회(직원)
     */
    @GetMapping("/meeting-detail-emp/{meetingReportCode}")
    public ResponseEntity<ResponseDto> detailMeetingReportForEmp(@PathVariable Integer meetingReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 회의보고서 조회", meetingService.selectDetailMeetingForEmp(meetingReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 회의보고서를 등록하기 위한 메소드
     */
    @PostMapping("/meeting-lists-emp")
    public ResponseEntity<ResponseDto> insertMeetingReport(@RequestBody MeetingDto meetingDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "회의보고서 작성", meetingService.insertMeetingReport(meetingDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 회의 보고서에 대한 comment작성을 위한 메소드(관리자)
     */
    @PutMapping("/meeting-lists-admin/{meetingReportCode}")
    public ResponseEntity<ResponseDto> updateMeetingReportForAdmin(@RequestBody MeetingDto meetingDto, @PathVariable int meetingReportCode){
        meetingDto.setMeetingReportCode(meetingReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"관리자용 보고서 업데이트", meetingService.updateMeetingReportForAdmin(meetingDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 회의보고서에 대해 수정을 위한 메소드
     */
    @PutMapping("/meeting-lists-emp/{meetingReportCode}")
    public ResponseEntity<ResponseDto> updateTripReportForEmp(@RequestBody MeetingDto meetingDto, @PathVariable int meetingReportCode){
        meetingDto.setMeetingReportCode(meetingReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 보고서 수정", meetingService.updateMeetingReportForEmp(meetingDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 회의보고서를 삭제하기 위한 메소드(관리자)
     */
    @DeleteMapping("/meeting-lists-admin/{meetingReportCode}")
    public ResponseEntity<ResponseDto> deleteTripReportForAdmin(@PathVariable Integer meetingReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", meetingService.deleteMeetingReportForAdmin(meetingReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 회의보고서를 삭제하기 위한 메소드(직원)
     */
    @DeleteMapping("/meeting-lists-emp/{meetingReportCode}")
    public ResponseEntity<ResponseDto> deleteTripReportForEmp(@PathVariable Integer meetingReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", meetingService.deleteMeetingReportForEmp(meetingReportCode)));
    }

}
