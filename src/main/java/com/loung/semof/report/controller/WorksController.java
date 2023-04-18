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

/**
 * @파일이름 : WorksController.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이지형
 * @클래스설명 : 업무보고서 crud 위한 controller.
 */
@RestController
@RequestMapping("/reports")
public class WorksController {

    private final WorksService worksService;

    public WorksController(WorksService worksService) {
        this.worksService = worksService;
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 업무보고서의 전체 리스트 조회 및 페이징(관리자)
     */
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

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 업무보고서의 읽지 않은 전체 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/works-n-lists-admin")
    public ResponseEntity<ResponseDto> selectAllWorkNStatusForAdmin(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = worksService.selectWorksReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(worksService.selectAllWorkNStatusForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 업무보고서의 읽은 리스트 조회 및 페이징(관리자)
     */
    @GetMapping("/works-y-lists-admin")
    public ResponseEntity<ResponseDto> selectAllWorkYStatusForAdmin(@RequestParam(name="offset", defaultValue = "1") String offset){

        int totalCount = worksService.selectWorksReportTotalForAdmin();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(worksService.selectAllWorkYStatusForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 실행", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 업무보고서의 전체 리스트 조회 및 페이징
     */
    @GetMapping("/works-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllWorksReportForEmpWithPaging(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = worksService.selectWorksReportTotalForEmp(empNo);
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(worksService.selectAllWorksReportForEmpWithPaging(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 업무보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 업무보고서의 읽지 않은 리스트 조회 및 페이징
     */
    @GetMapping("/works-n-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllWorkNStatusForEmp(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = worksService.selectWorksReportTotalForEmp(empNo);
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(worksService.selectAllWorkNStatusForEmp(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 업무보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 로그인한 직원이 작성한 업무보고서의 읽은 리스트 조회 및 페이징
     */
    @GetMapping("/works-y-lists-emp/{empNo}")
    public ResponseEntity<ResponseDto> selectAllWorkYStatusForEmp(
            @RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int empNo){

        int totalCount = worksService.selectWorksReportTotalForEmp(empNo);
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(worksService.selectAllWorkYStatusForEmp(selectCriteria, empNo));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 업무보고서 조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 작성한 보고서에 대한 상세조회(관리자)
     */
    @GetMapping("/works-detail-admin/{worksReportCode}")
    public ResponseEntity<ResponseDto> detailWorksReportForAdmin(@PathVariable Integer worksReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "관리자 업무보고서 조회", worksService.detailWorksReportForAdmin(worksReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 작성한 보고서에 대한 상세조회(직원)
     */
    @GetMapping("/works-detail-emp/{worksReportCode}")
    public ResponseEntity<ResponseDto> detailWorksReportForEmp(@PathVariable Integer worksReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 업무보고서 조회", worksService.detailWorksReportForEmp(worksReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 업무보고서를 등록하기 위한 메소드
     */
    @PostMapping("/works-lists-emp")
    public ResponseEntity<ResponseDto> insertWorksReport(@RequestBody WorksDto worksDto){
        System.out.println("worksDto = " + worksDto);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "업무보고서 작성", worksService.insertWorksReport(worksDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 업무보고서에 대한 comment작성을 위한 메소드(관리자)
     */
    @PutMapping("/works-lists-admin/{worksReportCode}")
    public ResponseEntity<ResponseDto> updateWorksReportForAdmin(@RequestBody WorksDto worksDto, @PathVariable int worksReportCode){
        worksDto.setWorksReportCode(worksReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"관리자용 보고서 업데이트", worksService.updateWorksReportForAdmin(worksDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 업무보고서에 대해 수정을 위한 메소드
     */
    @PutMapping("/works-lists-emp/{worksReportCode}")
    public ResponseEntity<ResponseDto> updateWorksReportForEmp(@RequestBody WorksDto worksDto, @PathVariable int worksReportCode){
        worksDto.setWorksReportCode(worksReportCode);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직원 보고서 수정", worksService.updateWorksReportForEmp(worksDto)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 업무보고서를 삭제하기 위한 메소드(관리자)
     */
    @DeleteMapping("/works-lists-admin/{worksReportCode}")
    public ResponseEntity<ResponseDto> deleteWorksReportForAdmin(@PathVariable Integer worksReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", worksService.deleteWorksReportForAdmin(worksReportCode)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 등록된 업무보고서를 삭제하기 위한 메소드(직원)
     */
    @DeleteMapping("/works-lists-emp/{worksReportCode}")
    public ResponseEntity<ResponseDto> deleteWorksReportForEmp(@PathVariable Integer worksReportCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "보고서 삭제", worksService.deleteWorksReportForEmp(worksReportCode)));
    }
}
