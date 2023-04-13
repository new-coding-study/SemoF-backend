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
    @GetMapping("/sales-detail-admin/{salesReportCode}")
    public ResponseEntity<ResponseDto> detailSalesReportForAdmin(@PathVariable Integer salesReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "관리자 영업보고서 조회", salesService.detailSalesReportForAdmin(salesReportCode)));
    }

    @GetMapping("/sales-detail-emp/{salesReportCode}")
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
