package com.loung.semof.reply.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.reply.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    int selectReplyTotal();
    List<ReplyDto> selectReplyWithPaging(SelectCriteria selectCriteria, int boardNo);

    int insertReply(String replyContent,int boardNo, int empNo);
//    int updateReplyForAdmin(ReplyDto replyDto);
    int updateReply(ReplyDto replyDto, int empNo, int boardNo, int replyCode);
    int deleteForAdmin(ReplyDto replyDto, int replyCode, int boardNo);
    int deleteForEmp(ReplyDto replyDto, int empNo, int boardNo, int replyCode);

    ReplyDto selectReply(int boardNo);
//
}
