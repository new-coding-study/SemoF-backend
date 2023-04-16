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

/**
 * @파일이름 : TripController.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이지형
 * @클래스설명 : 출장보고서 crud 위한 controller.
 */
@RestController
@RequestMapping("/reports")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 출장보고서의 전체 리스트 조회 및 페이징(관리자)
     */
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

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 출장보고서의 읽지 않은 전체 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/trip-n-lists-admin")
    public ResponseEntity<ResponseDto> selectAllTripNStatusForAdmin(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = tripService.selectTripReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(tripService.selectAllTripNStatusForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 출장보고서의 읽은 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/trip-y-lists-admin")
    public ResponseEntity<ResponseDto> selectAllTripYStatusForAdmin(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = tripService.selectTripReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(tripService.selectAllTripYStatusForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 출장보고서의 전체 리스트 조회 및 페이징
     */
    @GetMapping("/trip-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllTripReportForEmpWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = tripService.selectTripReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(tripService.selectAllTripReportForEmpWithPaging(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 출장보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 출장보고서의 읽지 않은 리스트 조회 및 페이징
     */
    @GetMapping("/trip-n-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllTripNStatusForEmp(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = tripService.selectTripReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(tripService.selectAllTripNStatusForEmp(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 출장보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 출장보고서의 읽은 리스트 조회 및 페이징
     */
    @GetMapping("/trip-y-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllTripYStatusForEmp(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = tripService.selectTripReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(tripService.selectAllTripYStatusForEmp(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 출장보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 작성한 보고서에 대한 상세조회(관리자)
     */
    @GetMapping("/trip-detail-admin/{tripReportCode}")
    public ResponseEntity<ResponseDto> detailTripReportForAdmin(@PathVariable Integer tripReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "관리자 출장보고서 조회", tripService.detailTripReportForAdmin(tripReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 작성한 보고서에 대한 상세조회(직원)
     */
    @GetMapping("/trip-detail-emp/{tripReportCode}")
    public ResponseEntity<ResponseDto> detailTripReportForEmp(@PathVariable Integer tripReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 출장보고서 조회", tripService.detailTripReportForEmp(tripReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 출장보고서를 등록하기 위한 메소드
     */
    @PostMapping("/trip-lists-emp")
    public ResponseEntity<ResponseDto> insertTripReport(@RequestBody TripDto tripDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "출장보고서 작성", tripService.insertTripReport(tripDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 출장보고서에 대한 comment작성을 위한 메소드(관리자)
     */
    @PutMapping("/trip-lists-admin/{tripReportCode}")
    public ResponseEntity<ResponseDto> updateTripReportForAdmin(@RequestBody TripDto tripDto, @PathVariable int tripReportCode){
        tripDto.setTripReportCode(tripReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"관리자용 보고서 업데이트", tripService.updateTripReportForAdmin(tripDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 출장보고서에 대해 수정을 위한 메소드
     */
    @PutMapping("/trip-lists-emp/{tripReportCode}")
    public ResponseEntity<ResponseDto> updateTripReportForEmp(@RequestBody TripDto tripDto, @PathVariable int tripReportCode){
        tripDto.setTripReportCode(tripReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 보고서 수정", tripService.updateTripReportForEmp(tripDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 출장보고서를 삭제하기 위한 메소드(관리자)
     */
    @DeleteMapping("/trip-lists-admin/{tripReportCode}")
    public ResponseEntity<ResponseDto> deleteTripReportForAdmin(@PathVariable Integer tripReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", tripService.deleteTripReportForAdmin(tripReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 출장보고서를 삭제하기 위한 메소드(직원)
     */
    @DeleteMapping("/trip-lists-emp/{tripReportCode}")
    public ResponseEntity<ResponseDto> deleteTripReportForEmp(@PathVariable Integer tripReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", tripService.deleteTripReportForEmp(tripReportCode)));
    }
}
