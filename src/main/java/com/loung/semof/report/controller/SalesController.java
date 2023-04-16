package com.loung.semof.report.controller;

import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.SalesDto;
import com.loung.semof.report.service.SalesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @파일이름 : SalesController.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이지형
 * @클래스설명 : 영업보고서 crud 위한 controller.
 */
@RestController
@RequestMapping("/reports")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 영업보고서의 전체 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/sales-lists-admin")
    public ResponseEntity<ResponseDto> selectAllSalesReportForAdminWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){

        int totalCount = salesService.selectSalesReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(salesService.selectAllSalesReportForAdminWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 영업보고서의 읽지 않은 전체 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/sales-n-lists-admin")
    public ResponseEntity<ResponseDto> selectAllSalesNStatusForAdmin(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = salesService.selectSalesReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(salesService.selectAllSalesNStatusForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 영업보고서의 읽은 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/sales-y-lists-admin")
    public ResponseEntity<ResponseDto> selectAllSalesYStatusForAdmin(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = salesService.selectSalesReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(salesService.selectAllSalesYStatusForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 영업보고서의 전체 리스트 조회 및 페이징
     */
    @GetMapping("/sales-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllSalesReportForEmpWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = salesService.selectSalesReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(salesService.selectAllSalesReportForEmpWithPaging(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 영업보고서의 읽지 않은 리스트 조회 및 페이징
     */
    @GetMapping("/sales-n-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllSalesNStatusForEmp(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = salesService.selectSalesReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(salesService.selectAllSalesNStatusForEmp(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 영업보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 영업보고서의 읽은 리스트 조회 및 페이징
     */
    @GetMapping("/sales-y-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllSalesYStatusForEmp(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = salesService.selectSalesReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(salesService.selectAllSalesYStatusForEmp(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 영업보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 작성한 보고서에 대한 상세조회(관리자)
     */
    @GetMapping("/sales-detail-admin/{salesReportCode}")
    public ResponseEntity<ResponseDto> detailSalesReportForAdmin(@PathVariable Integer salesReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "관리자 영업보고서 조회", salesService.detailSalesReportForAdmin(salesReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 작성한 보고서에 대한 상세조회(직원)
     */
    @GetMapping("/sales-detail-emp/{salesReportCode}")
    public ResponseEntity<ResponseDto> detailSalesReportForEmp(@PathVariable Integer salesReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 영업보고서 조회", salesService.detailSalesReportForEmp(salesReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 영업보고서를 등록하기 위한 메소드
     */
    @PostMapping("/sales-lists-emp")
    public ResponseEntity<ResponseDto> insertSalesReport(@RequestBody SalesDto salesDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "출장보고서 작성", salesService.insertSalesReport(salesDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 영업보고서에 대한 comment작성을 위한 메소드(관리자)
     */
    @PutMapping("/sales-lists-admin/{salesReportCode}")
    public ResponseEntity<ResponseDto> updateSalesReportForAdmin(@RequestBody SalesDto salesDto, @PathVariable int salesReportCode){
        salesDto.setSalesReportCode(salesReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"관리자용 보고서 업데이트", salesService.updateSalesReportForAdmin(salesDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 영업보고서에 대해 수정을 위한 메소드
     */
    @PutMapping("/sales-lists-emp/{salesReportCode}")
    public ResponseEntity<ResponseDto> updateSalesReportForEmp(@RequestBody SalesDto salesDto, @PathVariable int salesReportCode){
        salesDto.setSalesReportCode(salesReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"직원용 보고서 업데이트", salesService.updateSalesReportForEmp(salesDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 영업보고서를 삭제하기 위한 메소드(관리자)
     */
    @DeleteMapping("/sales-lists-admin/{salesReportCode}")
    public ResponseEntity<ResponseDto> deleteSalesReportForAdmin(@PathVariable Integer salesReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", salesService.deleteSalesReportForAdmin(salesReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 영업보고서를 삭제하기 위한 메소드(직원)
     */
    @DeleteMapping("/sales-lists-emp/{salesReportCode}")
    public ResponseEntity<ResponseDto> deleteSalesReportForEmp(@PathVariable Integer salesReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", salesService.deleteSalesReportForEmp(salesReportCode)));
    }
}
