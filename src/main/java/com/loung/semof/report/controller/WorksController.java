package com.loung.semof.report.controller;

import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.WorksDto;
import com.loung.semof.report.service.WorksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class WorksController {

    private final WorksService worksService;

    public WorksController(WorksService worksService) {
        this.worksService = worksService;
    }

    @GetMapping("/works-lists-admin")
    public ResponseEntity<ResponseDto> selectAllWorksReportForAdminWithPaging(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = worksService.selectWorksReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(worksService.selectAllWorksReportForAdminWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    @GetMapping("/works-lists-emp")
    public ResponseEntity<ResponseDto> selectAllWorksReportForEmpWithPaging(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @RequestParam int empNo){

        int totalCount = worksService.selectWorksReportTotalForEmp();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(worksService.selectAllWorksReportForEmpWithPaging(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 업무보고서 조회", responseDtoWithPaging));
    }

    @GetMapping("/works-lists-admin/{worksReportCode}")
    public ResponseEntity<ResponseDto> detailWorksReportForAdmin(@PathVariable Integer worksReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "관리자 업무보고서 조회", worksService.detailWorksReportForAdmin(worksReportCode)));
    }

    @GetMapping("/works-lists-emp/{worksReportCode}")
    public ResponseEntity<ResponseDto> detailWorksReportForEmp(@PathVariable Integer worksReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 업무보고서 조회", worksService.detailWorksReportForEmp(worksReportCode)));
    }

    @PostMapping("/works-lists-emp")
    public ResponseEntity<ResponseDto> insertWorksReport(@RequestBody WorksDto worksDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "업무보고서 작성", worksService.insertWorksReport(worksDto)));
    }

    @PutMapping("/works-lists-admin/{worksReportCode}")
    public ResponseEntity<ResponseDto> updateWorksReportForAdmin(@RequestBody WorksDto worksDto, @PathVariable int worksReportCode){
        worksDto.setWorksReportCode(worksReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"관리자용 보고서 업데이트", worksService.updateWorksReportForAdmin(worksDto)));
    }

    @PutMapping("/works-lists-emp/{worksReportCode}")
    public ResponseEntity<ResponseDto> updateWorksReportForEmp(@RequestBody WorksDto worksDto, @PathVariable int worksReportCode){
        worksDto.setWorksReportCode(worksReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 보고서 수정", worksService.updateWorksReportForEmp(worksDto)));
    }

    @DeleteMapping("/works-lists-admin/{worksReportCode}")
    public ResponseEntity<ResponseDto> deleteWorksReportForAdmin(@PathVariable Integer worksReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", worksService.deleteWorksReportForAdmin(worksReportCode)));
    }

    @DeleteMapping("/works-lists-emp/{worksReportCode}")
    public ResponseEntity<ResponseDto> deleteWorksReportForEmp(@PathVariable Integer worksReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", worksService.deleteWorksReportForEmp(worksReportCode)));
    }
}
