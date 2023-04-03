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

@RestController
@RequestMapping("/replies")
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/all-reply-lists/{boardNo}")
    public ResponseEntity<ResponseDto> selectReplyListWithPaging(
            @RequestParam(name = "offset", defaultValue = "1") String offset,
            @PathVariable int boardNo
    ) {
        int totalCount = replyService.selectReplyTotal();
        int limit = 15;
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


    @PostMapping("/replies")
    public ResponseEntity<ResponseDto> insertReply(@ModelAttribute String replyContent, @PathVariable int empNo, @PathVariable int boardNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED,"댓글 등록", replyService.insertReply(replyContent, empNo, boardNo)));
    }

//    @PutMapping("/reply-lists-management/{replyCode}")
//    public ResponseEntity<ResponseDto> updateReplyForAdmin(@ModelAttribute ReplyDto replyDto){
//        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 수정", replyService.updateReply(replyDto)));
//    }

    @PutMapping("/replies/{replyCode}")
    public ResponseEntity<ResponseDto> updateReply(@ModelAttribute ReplyDto replyDto, @PathVariable int empNo, @PathVariable int boardNo, @PathVariable int replyCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 수정", replyService.updateReply(replyDto, empNo, boardNo, replyCode)));
    }
    @DeleteMapping("/replies-delete-admin/{replyCode}")
    public ResponseEntity<ResponseDto> deleteReplyForAdmin(@ModelAttribute ReplyDto replyDto, @PathVariable int boardNo, @PathVariable int replyCode){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 삭제", replyService.deleteForAdmin(replyDto, boardNo, replyCode)));
    }

    @DeleteMapping("/replies-delete-emp/{replyCode}")
    public ResponseEntity<ResponseDto> deleteReply(@ModelAttribute ReplyDto replyDto, @PathVariable int empNo, @PathVariable int replyCode, @PathVariable int boardNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 삭제", replyService.deleteForEmp(replyDto, empNo, replyCode, boardNo)));
    }

}
