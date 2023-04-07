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

@RestController
@RequestMapping("/reports")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

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

    @GetMapping("/sales-lists-emp")
    public ResponseEntity<ResponseDto> selectAllSalesReportForEmpWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset, @RequestParam int empNo){

        int totalCount = salesService.selectSalesReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(salesService.selectAllSalesReportForEmpWithPaging(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    @GetMapping("/sales-lists-admin/{salesReportCode}")
    public ResponseEntity<ResponseDto> detailSalesReportForAdmin(@PathVariable Integer salesReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "관리자 영업보고서 조회", salesService.detailSalesReportForAdmin(salesReportCode)));
    }

    @GetMapping("/sales-lists-emp/{salesReportCode}")
    public ResponseEntity<ResponseDto> detailSalesReportForEmp(@PathVariable Integer salesReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 영업보고서 조회", salesService.detailSalesReportForEmp(salesReportCode)));
    }

    @PostMapping("/sales-lists-emp")
    public ResponseEntity<ResponseDto> insertSalesReport(@RequestBody SalesDto salesDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "출장보고서 작성", salesService.insertSalesReport(salesDto)));
    }

    @PutMapping("/sales-lists-admin/{salesReportCode}")
    public ResponseEntity<ResponseDto> updateSalesReportForAdmin(@RequestBody SalesDto salesDto, @PathVariable int salesReportCode){
        salesDto.setSalesReportCode(salesReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"관리자용 보고서 업데이트", salesService.updateSalesReportForAdmin(salesDto)));
    }

    @PutMapping("/sales-lists-emp/{salesReportCode}")
    public ResponseEntity<ResponseDto> updateSalesReportForEmp(@RequestBody SalesDto salesDto, @PathVariable int salesReportCode){
        salesDto.setSalesReportCode(salesReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"직원용 보고서 업데이트", salesService.updateSalesReportForEmp(salesDto)));
    }

    @DeleteMapping("/sales-lists-admin/{salesReportCode}")
    public ResponseEntity<ResponseDto> deleteSalesReportForAdmin(@PathVariable Integer salesReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", salesService.deleteSalesReportForAdmin(salesReportCode)));
    }

    @DeleteMapping("/sales-lists-emp/{salesReportCode}")
    public ResponseEntity<ResponseDto> deleteSalesReportForEmp(@PathVariable Integer salesReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", salesService.deleteSalesReportForEmp(salesReportCode)));
    }
}
