package com.loung.semof.approval.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loung.semof.approval.dto.*;
import com.loung.semof.approval.service.ApprovService;
import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.nio.charset.StandardCharsets;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/approvals")
public class ApprovController {
    private final ApprovService approvService;

    public ApprovController(ApprovService approvService) {
        this.approvService = approvService;
    }
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 미확인 결재문서를 삭제
     */
    @PostMapping(value="/line")
    public ResponseEntity<ResponseDto> insertApprovLine(@RequestBody ApprovLineDTO line) {
//        String requestWrapper;
//        String requestBody = new String(requestWrapper.getBytes(), StandardCharsets.UTF_8);
////        log.debug("Request Body: {}", requestBody);
        System.out.println("line = " + line);
//        System.out.println("orders = " + orders);

        log.info("----------------------------------------------------line = " + line);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재라인 등록", approvService.insertApprovLine(line)));
    }
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재 의견 등록()
     */
    @PostMapping(value = "/opinion")
    public ResponseEntity<ResponseDto> insertOpinion(@ModelAttribute ApprovOpinDTO opinion){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "의견등록", approvService.insertOpinion(opinion)));
    }


    @PostMapping(value="/approval", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> insertApproval(@RequestPart ApprovalDTO approval, @RequestPart(name = "fileList", required = false) List<MultipartFile> fileList) {
        log.info("결재등록 컨트롤러 호출" +fileList);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재상신", approvService.insertApproval(approval, fileList)));
    }
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재 문서 목록을 조회
     */
    @GetMapping("/approv-list")
    public ResponseEntity<ResponseDto> selectApprovalInWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){
        int totalCount = approvService.selectApprovalTotal();
        int limit = 10;
        int buttonAmount = 10;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(approvService.selectApprovalInWithPaging(selectCriteria));
//        responseDtoWithPaging.setData(approvService.selectLatestStatus());
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", responseDtoWithPaging));
    }
//    @GetMapping("/approv-in-box")
//    public ResponseEntity<ResponseDto> selectApprovalInWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){
//        int totalCount = approvService.selectApprovalTotal();
//        int limit = 10;
//        int buttonAmount = 10;
//
//        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
//
//        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
//        responseDtoWithPaging.setPageInfo(selectCriteria);
//        responseDtoWithPaging.setData(approvService.selectApprovalListWithPaging(selectCriteria));
//
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", responseDtoWithPaging));
//    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 설정된 결재라인 조회
     */
    @GetMapping("/line-list")
    public ResponseEntity<ResponseDto> selectApprovLineListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){
        int totalCount = approvService.selectApprovalLineTotal();
        int limit = 10;
        int buttonAmount = 10;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(approvService.selectApprovLineListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", responseDtoWithPaging));
    }
    @GetMapping("/lines")
    public ResponseEntity<ResponseDto> selectLineList(){
        List<ApprovLineDTO> lineList = approvService.selectLineList();
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", lineList));
    }
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재 문서 상세조회
     */
    @GetMapping("/approval/{approvNo}")
    public ResponseEntity<ResponseDto> selectApproval(@PathVariable Integer approvNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재상세조회", approvService.selectApproval(approvNo)));
    }
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재라인 수정
     */
    @PutMapping(value = "/line")
    public ResponseEntity<ResponseDto> updateApprovLine(@ModelAttribute ApprovLineDTO line, List<ApprovOrderDTO> orders){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재라인 업데이트", approvService.updateApprovLine(line, orders)));
    }

    @GetMapping("/line/{lineNo}")
    public ResponseEntity<ResponseDto> selectLineDetail(@PathVariable Integer lineNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "라인 상세조회", approvService.selectLineDetail(lineNo)));

    }

//    @PutMapping(value = "/approval", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<ResponseDto> updateApproval(@RequestPart ApprovalDTO approval, @RequestPart(name = "fileList", required = false) List<MultipartFile> file, @RequestPart List<ApprovContentDTO> contents){
//
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재서류 업데이트", approvService.updateApproval(approval, file, contents)));
//    }
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재처리(결재 상태를 업데이트)
     */
    @PutMapping(value="/status")
    public ResponseEntity<ResponseDto> updateStatus(@ModelAttribute ApprovStatusDTO status){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재처리", approvService.updateStatus(status)));
    }
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재라인을 삭제
     */
//    @DeleteMapping(value = "/line")
//    public ResponseEntity<ResponseDto> deleteApprovLine(ApprovLineDTO line){
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재라인 삭제", approvService.deleteApprovLine(line)));
//    }
    @DeleteMapping(value = "/line/{lineNo}")
    public ResponseEntity<ResponseDto> deleteApprovLine(@PathVariable Integer lineNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재라인 삭제", approvService.deleteApprovLine(lineNo)));
    }
/**
 * @작성일 : 2023.03.23
 * @작성자 : 박유리
 * @메소드설명 : 미확인 결재문서를 삭제
 */
//    @DeleteMapping(value = "/approval")
//    public ResponseEntity<ResponseDto> deleteApproval(ApprovalDTO approval){
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재서류 삭제", approvService.deleteApproval(approval)));
//    }

    @DeleteMapping(value = "/approval/{approvNo}")
    public ResponseEntity<ResponseDto> deleteApproval(Integer approvNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재서류 삭제", approvService.deleteApproval(approvNo)));
    }

    @GetMapping("/form-title")
    public ResponseEntity<ResponseDto> selectFormTitle(){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재유형별 목록조회", approvService.selectFormTitle()));
    }

    @GetMapping("/branches")
    public ResponseEntity<ResponseDto> selectBranch(){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "부서조회", approvService.selectBranch()));
    }

    @GetMapping("/job-name")
    public ResponseEntity<ResponseDto> selectJobNEmpName(){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직급, 이름조회", approvService.selectJobNEmpName()));
    }

//    @PostMapping(value = "/orders")
//    public ResponseEntity<ResponseDto> insertOrders(@RequestBody List<ApprovOrderDTO> orders){
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재순서등록", approvService.insertApprovOrders(orders)));
//    }
//    @PostMapping(value="/lines")
//    public ResponseEntity<ResponseDto> insertApprovLines(@RequestBody List<ApprovLineDTO> lines){
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재라인 등록", approvService.insertApprovLines(lines)));
//    }
//    @PostMapping(value = "/line")
//    public ResponseEntity<ResponseDto> insertLine(@ModelAttribute ApprovLineDTO line){
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "라인등록", approvService.insertLine(line)));
//    }

    @GetMapping("/dept")
    public ResponseEntity<ResponseDto> selectDept(){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "부서조회", approvService.selectDept()));

    }
}
