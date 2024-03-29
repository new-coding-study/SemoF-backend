package com.loung.semof.approval.controller;

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
     * @메소드설명 : 결재 라인 등록(ROLE_ADMIN)
     */
    @PostMapping(value="/line")
    public ResponseEntity<ResponseDto> insertApprovLine(@RequestBody ApprovLineDTO line) {

        System.out.println("line = " + line);

        log.info("----------------------------------------------------line = " + line);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재라인 등록", approvService.insertApprovLine(line)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재 의견 등록(ROLE_ADMIN)
     */
    @PostMapping(value = "/opinion")
    public ResponseEntity<ResponseDto> insertOpinion(@ModelAttribute ApprovOpinDTO opinion){
        log.info(String.valueOf(opinion));

        System.out.println("opinion = " + opinion);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "의견등록", approvService.insertOpinion(opinion)));
    }

/**
 * @작성일 : 2023-04-07
 * @작성자 : 박유리
 * @메소드설명 : 결재등록(ROLE_USER)
 */
    @PostMapping(value="/approval", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> insertApproval(@RequestPart ApprovalDTO approval, @RequestPart(name = "fileList", required = false) List<MultipartFile> fileList) {
        log.info("결재등록 컨트롤러 호출" +fileList);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재상신", approvService.insertApproval(approval, fileList)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재 문서 목록을 조회(상신, 수신, 완료 나누기)
     */
    @GetMapping("/approv-in/{empNo}")
    public ResponseEntity<ResponseDto> selectApprovalInWithPaging(@PathVariable Integer empNo, @RequestParam(name = "offset", defaultValue = "1") String offset){
        int totalCount = approvService.selectApprovalTotal(empNo);
        int limit = 10;
        int buttonAmount = 10;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(approvService.selectApprovalInWithPaging(empNo, selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 설정된 결재라인 조회(ROLE_USER)
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

    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재 수신함
     */
    @GetMapping("/approv-out/{empNo}")
    public ResponseEntity<ResponseDto> selectApprovalOutWithPaging(@PathVariable Integer empNo, @RequestParam(name = "offset", defaultValue = "1") String offset){
        int totalCount = approvService.selectApprovOutTotal(empNo);
        System.out.println("totalCount = " + totalCount);
        int limit = 10;
        int buttonAmount = 10;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(approvService.selectApprovalOutWithPaging(empNo, selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", responseDtoWithPaging));
    }

    @GetMapping("/fin-approv/{empNo}")
    public ResponseEntity<ResponseDto> selectFinApprovalInWithPaging(@PathVariable Integer empNo, @RequestParam(name = "offset", defaultValue = "1") String offset){
        int totalCount = approvService.selectFinApprovalTotal(empNo);
        System.out.println("totalCount = " + totalCount);
        int limit = 10;
        int buttonAmount = 10;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(approvService.selectFinApprovalInWithPaging(empNo, selectCriteria));
//        responseDtoWithPaging.setData(approvService.selectLatestStatus());
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", responseDtoWithPaging));
    }
    @GetMapping("/fin-approv-out/{empNo}")
    public ResponseEntity<ResponseDto> selectFinApprovalOutWithPaging(@PathVariable Integer empNo, @RequestParam(name = "offset", defaultValue = "1") String offset){
        int totalCount = approvService.selectFinApprovOutTotal(empNo);
        System.out.println("totalCount = " + totalCount);
        int limit = 10;
        int buttonAmount = 10;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(approvService.selectFinApprovalOutWithPaging(empNo, selectCriteria));
//        responseDtoWithPaging.setData(approvService.selectLatestStatus());
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", responseDtoWithPaging));
    }

    /**
     * @작성일 : 2023-04-07
     * @작성자 : 박유리
     * @메소드설명 : 페이징처리 없는 결재라인조회, 결재 등록 시 사용된다.(ROLE_USER)
     */
    @GetMapping("/lines")
    public ResponseEntity<ResponseDto> selectLineList(){
        List<ApprovLineDTO> lineList = approvService.selectLineList();
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", lineList));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재 문서 상세조회 (ROLE_USER)
     */
    @GetMapping("/approval/{approvNo}")
    public ResponseEntity<ResponseDto> selectApproval(@PathVariable Integer approvNo){
        System.out.println("결재 상세 페이지 컨트롤러");
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재상세조회", approvService.selectApproval(approvNo)));
    }

    /**
     * @작성일 : 2023-04-07
     * @작성자 : 박유리
     * @메소드설명 : 결재라인 상세조회 라인 수정시 이용되는 메소드(ROLE_ADMIN)
     */
    @GetMapping("/line/{lineNo}")
    public ResponseEntity<ResponseDto> selectLineDetail(@PathVariable Integer lineNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "라인 상세조회", approvService.selectLineDetail(lineNo)));

    }

    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재 문서 업데이트
     */
    @PutMapping(value = "/approval/{approvNo}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDto> updateApproval(@RequestPart ApprovalDTO approval, @RequestPart(name = "fileList", required = false) List<MultipartFile> file, @PathVariable Integer approvNo){
        approval.setApprovNo(approvNo);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재서류 업데이트", approvService.updateApproval(approval, file)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재처리(결재 상태를 업데이트)ROLE_ADMIN
     */
    @PutMapping(value="/state/{lineNo}/{approvNo}/{empNo}")
    public ResponseEntity<ResponseDto> updateStatus(@PathVariable Integer lineNo, @PathVariable Integer approvNo, @PathVariable Long empNo, @RequestParam(name ="this") String status ){
        System.out.println("lineNo = " + lineNo);
        System.out.println("approvNo = " + approvNo);
        System.out.println("empNo = " + empNo);
        System.out.println("status = " + status);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재처리", approvService.updateStatus(approvNo, lineNo, empNo, status)));
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재라인을 삭제(ROLE_ADMIN)
     */
//    @DeleteMapping(value = "/line")
//    public ResponseEntity<ResponseDto> deleteApprovLine(ApprovLineDTO line){
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재라인 삭제", approvService.deleteApprovLine(line)));
//    }

    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재라인의 상태를 n으로 바꾼다(참조성 테이블이므로 삭제하지 않음)
     */
    @PutMapping(value = "/line/{lineNo}")
    public ResponseEntity<ResponseDto> deleteApprovLine(@PathVariable Integer lineNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재라인 삭제", approvService.deleteApprovLine(lineNo)));
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
    public ResponseEntity<ResponseDto> deleteApproval(@PathVariable Integer approvNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재서류 삭제", approvService.deleteApproval(approvNo)));
    }

    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재 유형별 작성 목록 조회
     */
    @GetMapping("/form-title")
    public ResponseEntity<ResponseDto> selectFormTitle(){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재유형별 목록조회", approvService.selectFormTitle()));
    }

    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재 라인 추가 시 지점 목록 조회
     */
    @GetMapping("/branches")
    public ResponseEntity<ResponseDto> selectBranch(){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "부서조회", approvService.selectBranch()));
    }

    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재 라인 등록, 조회 시 직급 명 조회
     */
    @GetMapping("/job-name")
    public ResponseEntity<ResponseDto> selectJobNEmpName(){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "직급, 이름조회", approvService.selectJobNEmpName()));
    }
    
    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재라인 등록 시 부서명 조회
     */
    @GetMapping("/dept")
    public ResponseEntity<ResponseDto> selectDept(){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "부서조회", approvService.selectDept()));

    }

    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재 의견 조회
     */
    @GetMapping("/opinions/{approvNo}")
    public ResponseEntity<ResponseDto> selectOpinion(@PathVariable  Integer approvNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "의견조회", approvService.selectOpinion(approvNo)));
    }

    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재 상태조회
     */
    @GetMapping("/statuses/{approvNo}")
    public ResponseEntity<ResponseDto> selectStatus(@PathVariable Integer approvNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "상태값 목록", approvService.selectStatus(approvNo)));
    }

    /**
     * @작성일 : 2023-04-18
     * @작성자 : 박유리
     * @메소드설명 : 결재문서에 등록된 파일 목록 조회
     */
    @GetMapping("/files/{approvNo}")
    public ResponseEntity<ResponseDto> selectFiles(@PathVariable Integer approvNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "파일 목록 조회", approvService.selectFiles(approvNo)));
    }
}
