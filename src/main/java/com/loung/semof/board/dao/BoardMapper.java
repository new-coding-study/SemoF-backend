package com.loung.semof.board.dao;

import com.loung.semof.board.dto.BoardDto;
import com.loung.semof.common.paging.SelectCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int selectNoticeTotal();

    List<BoardDto> selectNoticeListWithPaging(SelectCriteria selectCriteria);

    int selectPostingTotal();

    List<BoardDto> selectPostingListWithPaging(SelectCriteria selectCriteria);
}
