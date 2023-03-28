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
@RequestMapping("/boardPosting-lists/{boardNo}")
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/reply-lists")
    public ResponseEntity<ResponseDto> selectReplyListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){
        int totalCount = replyService.selectReplyTotal();
        int limit = 15;
        int buttonAmount = 3;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(replyService.selectReplyListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "댓글 조회", responseDtoWithPaging));
    }

    @PostMapping("/reply-lists")
    public ResponseEntity<ResponseDto> insertReply(@ModelAttribute ReplyDto replyDto, @PathVariable int empNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED,"댓글 등록", replyService.insertReply(replyDto, empNo)));
    }

    @PutMapping("/reply-lists-management/{replyCode}")
    public ResponseEntity<ResponseDto> updateReplyForAdmin(@ModelAttribute ReplyDto replyDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 수정", replyService.updateReplyForAdmin(replyDto)));
    }

    @PutMapping("/reply-lists/{replyCode}")
    public ResponseEntity<ResponseDto> updateReply(@ModelAttribute ReplyDto replyDto, @PathVariable int empNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 수정", replyService.updateReply(replyDto, empNo)));
    }
    @DeleteMapping("/reply-lists-management/{replyCode}")
    public ResponseEntity<ResponseDto> deleteReplyForAdmin(@ModelAttribute ReplyDto replyDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 삭제", replyService.deleteReplyForAdmin(replyDto)));
    }
//
    @DeleteMapping("/reply-lists/{replyCode}")
    public ResponseEntity<ResponseDto> deleteReply(@ModelAttribute ReplyDto replyDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"댓글 삭제", replyService.deleteReply(replyDto)));
    }
}
