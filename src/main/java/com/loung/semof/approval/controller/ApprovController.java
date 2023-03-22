package com.loung.semof.approval.controller;

import com.loung.semof.approval.dto.ApprovOpinDTO;
import com.loung.semof.approval.service.ApprovService;
import com.loung.semof.common.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/approval")
public class ApprovController {
    private final ApprovService approvService;

    public ApprovController(ApprovService approvService) {
        this.approvService = approvService;
    }

    @PostMapping(value = "/opinion")
    public ResponseEntity<ResponseDto> insertOpinion(@ModelAttribute ApprovOpinDTO opinion){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "의견등록", approvService.insertOpinion(opinion)));
    }
}
