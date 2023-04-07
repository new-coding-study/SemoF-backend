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

@RestController
@RequestMapping("/reports")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/meeting-lists-admin")
    public ResponseEntity<ResponseDto> selectAllTripReportForAdminWithPaging(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = meetingService.selectMeetingReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(meetingService.selectAllMeetingReportForAdminWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    @GetMapping("/meeting-lists-emp")
    public ResponseEntity<ResponseDto> selectAllMeetingReportForEmpWithPaging(@RequestParam(name="offset", defaultValue = "1") String offset, @RequestParam int empNo){

        int totalCount = meetingService.selectMeetingReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(meetingService.selectAllMeetingReportForEmpWithPaging(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    @GetMapping("/meeting-lists-admin/{meetingReportCode}")
    public ResponseEntity<ResponseDto> detailMeetingReportForAdmin(@PathVariable Integer meetingReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "관리자 회의보고서 조회", meetingService.selectDetailMeetingForAdmin(meetingReportCode)));
    }

    @GetMapping("/meeting-lists-emp/{meetingReportCode}")
    public ResponseEntity<ResponseDto> detailMeetingReportForEmp(@PathVariable Integer meetingReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 회의보고서 조회", meetingService.selectDetailMeetingForEmp(meetingReportCode)));
    }

    @PostMapping("/meeting-lists-emp")
    public ResponseEntity<ResponseDto> insertMeetingReport(@RequestBody MeetingDto meetingDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "회의보고서 작성", meetingService.insertMeetingReport(meetingDto)));
    }

    @PutMapping("/meeting-lists-admin/{meetingReportCode}")
    public ResponseEntity<ResponseDto> updateMeetingReportForAdmin(@RequestBody MeetingDto meetingDto, @PathVariable int meetingReportCode){
        meetingDto.setMeetingReportCode(meetingReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"관리자용 보고서 업데이트", meetingService.updateMeetingReportForAdmin(meetingDto)));
    }

    @PutMapping("/meeting-lists-emp/{meetingReportCode}")
    public ResponseEntity<ResponseDto> updateTripReportForEmp(@RequestBody MeetingDto meetingDto, @PathVariable int meetingReportCode){
        meetingDto.setMeetingReportCode(meetingReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 보고서 수정", meetingService.updateMeetingReportForEmp(meetingDto)));
    }

    @DeleteMapping("/meeting-lists-admin/{meetingReportCode}")
    public ResponseEntity<ResponseDto> deleteTripReportForAdmin(@PathVariable Integer meetingReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", meetingService.deleteMeetingReportForAdmin(meetingReportCode)));
    }

    @DeleteMapping("/meeting-lists-emp/{meetingReportCode}")
    public ResponseEntity<ResponseDto> deleteTripReportForEmp(@PathVariable Integer meetingReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", meetingService.deleteMeetingReportForEmp(meetingReportCode)));
    }

}
