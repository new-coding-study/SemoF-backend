package com.loung.semof.approval.controller;

import com.loung.semof.approval.dto.*;
import com.loung.semof.approval.service.ApprovService;
import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/approval")
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
    public ResponseEntity<ResponseDto> insertApprovLine(@ModelAttribute ApprovLineDTO line, List<ApprovOrderDTO> orders){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재라인 등록", approvService.insertApprovLine(line, orders)));
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
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재 상신(결재문서 등록)
     */
    @PostMapping(value="/approval")
    public ResponseEntity<ResponseDto> insertApproval(@ModelAttribute ApprovalDTO approval, List<ApprovFileDTO> file,List<ApprovContentDTO> contents) {
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재상신", approvService.insertApproval(approval, file, contents)));
    }
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 결재 문서 목록을 조회
     */
    @GetMapping("/approvList")
    public ResponseEntity<ResponseDto> selectApprovalListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){
        int totalCount = approvService.selectApprovalTotal();
        int limit = 10;
        int buttonAmount = 10;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(approvService.selectApprovalListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재목록조회", responseDtoWithPaging));
    }
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 설정된 결재라인 조회
     */
    @GetMapping("/lineList")
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
    /**
     * @작성일 : 2023.03.23
     * @작성자 : 박유리
     * @메소드설명 : 미확인, 반려 결재문서를 수정
     */
    @PutMapping(value = "/approval")
    public ResponseEntity<ResponseDto> updateApproval(@ModelAttribute ApprovalDTO approval, List<ApprovFileDTO> file,List<ApprovContentDTO> contents){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재서류 업데이트", approvService.updateApproval(approval, file, contents)));
    }
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
    @DeleteMapping(value = "/line")
    public ResponseEntity<ResponseDto> deleteApprovLine(ApprovLineDTO line){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재라인 삭제", approvService.deleteApprovLine(line)));
    }
/**
 * @작성일 : 2023.03.23
 * @작성자 : 박유리
 * @메소드설명 : 미확인 결재문서를 삭제
 */
    @DeleteMapping(value = "/approval")
    public ResponseEntity<ResponseDto> deleteApproval(ApprovalDTO approval){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재서류 삭제", approvService.deleteApproval(approval)));
    }
}
