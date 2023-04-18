package com.loung.semof.reply.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.reply.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    int selectReplyTotal(int boardNo);
    List<ReplyDto> selectReplyWithPaging(SelectCriteria selectCriteria, int boardNo);
    int insertReply(ReplyDto replyDto);
    int deleteForAdmin(int boardNo, int replyCode);

//    int deleteForEmp(ReplyDto replyDto);

//    int updateReply(ReplyDto replyDto);

    int deleteForEmp(int empNo, int replyCode);
//
}
