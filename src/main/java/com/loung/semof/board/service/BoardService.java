package com.loung.semof.board.service;

import com.loung.semof.board.dao.BoardMapper;
import com.loung.semof.board.dto.BoardDto;
import com.loung.semof.common.paging.SelectCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public int selectNoticeTotal() {
        int result = boardMapper.selectNoticeTotal();

        return result;
    }

    public Object selectNoticeListWithPaging(SelectCriteria selectCriteria) {
        List<BoardDto> boardList = boardMapper.selectNoticeListWithPaging(selectCriteria);
        for(int i = 0; i < boardList.size(); i++);
        return boardList;
    }
}
