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

    @PostMapping(value="/line")
    public ResponseEntity<ResponseDto> insertApprovLine(@ModelAttribute ApprovLineDTO line, List<ApprovOrderDTO> orders){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재라인 등록", approvService.insertApprovLine(line, orders)));
    }

    @PostMapping(value = "/opinion")
    public ResponseEntity<ResponseDto> insertOpinion(@ModelAttribute ApprovOpinDTO opinion){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "의견등록", approvService.insertOpinion(opinion)));
    }

    @PostMapping(value="/approval")
    public ResponseEntity<ResponseDto> insertApproval(@ModelAttribute ApprovalDTO approval, List<ApprovFileDTO> file,List<ApprovContentDTO> contents) {
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재상신", approvService.insertApproval(approval, file, contents)));
    }

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

    @GetMapping("/approval/{approvNo}")
    public ResponseEntity<ResponseDto> selectApproval(@PathVariable Integer approvNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재상세조회", approvService.selectApproval(approvNo)));
    }

    @PutMapping(value = "/line")
    public ResponseEntity<ResponseDto> updateApprovLine(@ModelAttribute ApprovLineDTO line, List<ApprovOrderDTO> orders){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재라인 업데이트", approvService.updateApprovLine(line, orders)));
    }

    @PutMapping(value = "/approval")
    public ResponseEntity<ResponseDto> updateApproval(@ModelAttribute ApprovalDTO approval, List<ApprovFileDTO> file,List<ApprovContentDTO> contents){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재서류 업데이트", approvService.updateApproval(approval, file, contents)));
    }

    @PutMapping(value="/status")
    public ResponseEntity<ResponseDto> updateStatus(@ModelAttribute ApprovStatusDTO status){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "결재처리", approvService.updateStatus(status)));
    }

    @DeleteMapping(value = "/line")
    public ResponseEntity<ResponseDto> deleteApprovLine(ApprovLineDTO line){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재라인 삭제", approvService.deleteApprovLine(line)));
    }

    @DeleteMapping(value = "/approval")
    public ResponseEntity<ResponseDto> deleteApproval(ApprovalDTO approval){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "결재서류 삭제", approvService.deleteApproval(approval)));
    }
}
