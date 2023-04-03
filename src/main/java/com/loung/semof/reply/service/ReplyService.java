package com.loung.semof.reply.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.reply.dao.ReplyMapper;
import com.loung.semof.reply.dto.ReplyDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyService {
    private final ReplyMapper replyMapper;

    public ReplyService(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }
    public int selectReplyTotal(){
        int result = replyMapper.selectReplyTotal();
        return result;
    }
//
    public Object selectReplyWithPaging(SelectCriteria selectCriteria, int boardNo){

        System.out.println("서비스호출 확인");
        List<ReplyDto> replyList = replyMapper.selectReplyWithPaging(selectCriteria, boardNo);
        for(int i = 0; i<replyList.size(); i++);
        return replyList;
    }

    @Transactional
    public Object insertReply(String replyContent, int empNo, int boardNo){
        int result = replyMapper.insertReply(replyContent, empNo, boardNo);
        return (result > 0)? "댓글추가 성공" : "댓글추가 실패";
    }

//    @Transactional
//    public Object updateReplyForAdmin(ReplyDto replyDto){
//        int result = replyMapper.updateReplyForAdmin(replyDto);
//        return (result > 0)? "댓글 수정완료":"댓글 수정 실패";
//    }

    @Transactional
    public Object updateReply(ReplyDto replyDto, int empNo, int boardNo, int replyCode){
        int result = replyMapper.updateReply(replyDto, empNo, boardNo, replyCode);
        return (result > 0)? "댓글 수정완료":"댓글 수정 실패";
    }

    @Transactional
    public Object deleteForAdmin(ReplyDto replyDto, int replyCode, int boardNo){
        int result = replyMapper.deleteForAdmin(replyDto, replyCode, boardNo);
        return (result > 0)? "댓글 삭제 완료" : "댓글 삭제 완료";
    }

    @Transactional
    public Object deleteForEmp(ReplyDto replyDto, int empNo, int replyCode, int boardNo){
        int result = replyMapper.deleteForEmp(replyDto, empNo, replyCode, boardNo);
        return (result > 0)? "댓글 삭제 완료" : "댓글 삭제 완료";
    }
}
