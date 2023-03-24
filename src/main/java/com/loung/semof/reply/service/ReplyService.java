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

    public Object selectReplyListWithPaging(SelectCriteria selectCriteria){
        List<ReplyDto> replyList = replyMapper.selectReplyListWithPaging(selectCriteria);
        for(int i = 0; i<replyList.size(); i++);
        return replyList;
    }

    @Transactional
    public Object insertReply(ReplyDto replyDto, int empNo){
        int result = replyMapper.insertReply(replyDto, empNo);
        return (result > 0)? "댓글추가 성공" : "댓글추가 실패";
    }

    @Transactional
    public Object updateReplyForAdmin(ReplyDto replyDto){
        int result = replyMapper.updateReplyForAdmin(replyDto);
        return (result > 0)? "댓글 수정완료":"댓글 수정 실패";
    }

    @Transactional
    public Object updateReply(ReplyDto replyDto, int empNo){
        int result = replyMapper.updateReply(replyDto, empNo);
        return (result > 0)? "댓글 수정완료":"댓글 수정 실패";
    }

    @Transactional
    public Object deleteReplyForAdmin(ReplyDto replyDto){
        int result = replyMapper.deleteReplyForAdmin(replyDto);
        return (result > 0)? "댓글 삭제 완료" : "댓글 삭제 완료";
    }

    @Transactional
    public Object deleteReply(ReplyDto replyDto){
        int result = replyMapper.deleteReply(replyDto);
        return (result > 0)? "댓글 삭제 완료" : "댓글 삭제 완료";
    }
}
