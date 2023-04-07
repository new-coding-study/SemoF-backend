package com.loung.semof.reply.controller;


import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.reply.dto.ReplyDto;
import com.loung.semof.reply.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @파일이름 : ReplyController.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-05
 * @작성자 : 이지형
 * @클래스설명 : 댓글 crud를 위한 controller.
 */
@RestController
@RequestMapping("/replies")
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 조회를 위한 메소드 with paging
     */
    @GetMapping("/all-reply-lists/{boardNo}")
    public ResponseEntity<ResponseDto> selectReplyListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset, @PathVariable int boardNo) {

        int totalCount = replyService.selectReplyTotal(boardNo);
        System.out.println("totalCount = " + totalCount);
        int limit = 5;
        int buttonAmount = 3;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        System.out.println("컨트롤러 호출 확인");
        System.out.println("selectCriteria" + selectCriteria);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(replyService.selectReplyWithPaging(selectCriteria, boardNo));


        ResponseDto responseDto = new ResponseDto(HttpStatus.OK, "댓글 조회", responseDtoWithPaging);
        return ResponseEntity.ok().body(responseDto);
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 등록.
     */
    @PostMapping("/all-reply-lists")
    public ResponseEntity<ResponseDto> insertReply(@RequestBody ReplyDto replyDto){
        System.out.println("replyDto = " + replyDto);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED,"댓글 등록", replyService.insertReply(replyDto)));

    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 삭제(관리자).
     */
    @DeleteMapping("/replies-delete-admin/{boardNo}/{replyCode}")
    public ResponseEntity<ResponseDto> deleteReplyForAdmin(@PathVariable int boardNo, @PathVariable int replyCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 삭제", replyService.deleteForAdmin(boardNo, replyCode)));
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 삭제(직원).
     */
    @DeleteMapping("/replies-delete-emp/{replyCode}/{empNo}")
    public ResponseEntity<ResponseDto> deleteReply(@PathVariable int empNo, @PathVariable int replyCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 삭제", replyService.deleteForEmp(empNo, replyCode)));
    }

}
