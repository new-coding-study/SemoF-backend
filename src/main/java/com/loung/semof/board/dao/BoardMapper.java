package com.loung.semof.board.dao;

import com.loung.semof.board.dto.BoardDto;
import com.loung.semof.common.paging.SelectCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일이름 : BoardMapper.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이지형
 * @클래스설명 : 공지사항 및 게시글 위한 mapper.
 */

@Mapper
public interface BoardMapper {
    int selectNoticeTotal();
    List<BoardDto> selectNoticeListWithPaging(SelectCriteria selectCriteria);
    int selectPostingTotal();
    List<BoardDto> selectPostingListWithPaging(SelectCriteria selectCriteria);
    BoardDto selectNoticeDetail(Integer boardNo);
    int selectNoticeTopTotal();
    BoardDto selectPostingDetailForEmp(Integer boardNo);
    int insertAllBoard(BoardDto boardDto);
    int insertPosting(BoardDto boardDto);
    int updateBoardAll(BoardDto boardDto);
    int updatePosting(BoardDto boardDto);
    int deleteBoardForAdmin(Integer boardNo);
    int deleteBoardForEmp(int empNo, Integer boardNo);

}
