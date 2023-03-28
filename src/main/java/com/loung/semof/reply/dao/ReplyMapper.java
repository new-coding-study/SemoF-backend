package com.loung.semof.reply.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.reply.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    int selectReplyTotal();
    List<ReplyDto> selectReplyListWithPaging(SelectCriteria selectCriteria);
    int insertReply(ReplyDto replyDto, int empNo);
    int updateReplyForAdmin(ReplyDto replyDto);
    int updateReply(ReplyDto replyDto, int empNo);
    int deleteReplyForAdmin(ReplyDto replyDto);
    int deleteReply(ReplyDto replyDto);
//
}
