package com.loung.semof.report.controller;


import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.TripDto;
import com.loung.semof.report.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/trip-lists-admin")
    public ResponseEntity<ResponseDto> selectAllTripReportForAdminWithPaging(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = tripService.selectTripReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(tripService.selectAllTripReportForAdminWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    @GetMapping("/trip-lists-emp")
    public ResponseEntity<ResponseDto> selectAllTripReportForEmpWithPaging(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @RequestParam int empNo){

        int totalCount = tripService.selectTripReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(tripService.selectAllTripReportForEmpWithPaging(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 출장보고서 조회", responseDtoWithPaging));
    }

    @GetMapping("/trip-lists-admin/{tripReportCode}")
    public ResponseEntity<ResponseDto> detailTripReportForAdmin(@PathVariable Integer tripReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "관리자 출장보고서 조회", tripService.detailTripReportForAdmin(tripReportCode)));
    }

    @GetMapping("/trip-lists-emp/{tripReportCode}")
    public ResponseEntity<ResponseDto> detailTripReportForEmp(@PathVariable Integer tripReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 출장보고서 조회", tripService.detailTripReportForEmp(tripReportCode)));
    }

    @PostMapping("/trip-lists-emp")
    public ResponseEntity<ResponseDto> insertTripReport(@ModelAttribute TripDto tripDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "출장보고서 작성", tripService.insertTripReport(tripDto)));
    }

    @PutMapping("/trip-lists-admin/{tripReportCode}")
    public ResponseEntity<ResponseDto> updateTripReportForAdmin(@ModelAttribute TripDto tripDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"관리자용 보고서 업데이트", tripService.updateTripReportForAdmin(tripDto)));
    }

    @PutMapping("/trip-lists-emp/{tripReportCode}")
    public ResponseEntity<ResponseDto> updateTripReportForEmp(@RequestBody TripDto tripDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 보고서 수정", tripService.updateTripReportForEmp(tripDto)));
    }

    @DeleteMapping("/trip-lists-admin/{tripReportCode}")
    public ResponseEntity<ResponseDto> deleteTripReportForAdmin(@PathVariable Integer tripReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", tripService.deleteTripReportForAdmin(tripReportCode)));
    }

    @DeleteMapping("/trip-lists-emp/{tripReportCode}")
    public ResponseEntity<ResponseDto> deleteTripReportForEmp(@PathVariable Integer tripReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", tripService.deleteTripReportForEmp(tripReportCode)));
    }
}
