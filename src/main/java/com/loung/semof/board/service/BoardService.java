package com.loung.semof.board.service;

import com.loung.semof.board.dao.BoardMapper;
import com.loung.semof.board.dto.BoardDto;
import com.loung.semof.common.paging.SelectCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public int selectPostingTotal() {
        int result = boardMapper.selectPostingTotal();

        return result;
    }

    public Object selectPostingListWithPaging(SelectCriteria selectCriteria) {
        List<BoardDto> boardList = boardMapper.selectPostingListWithPaging(selectCriteria);
        for(int i = 0; i < boardList.size(); i++);
        return boardList;
    }

    public Object selectNoticeDetail(Integer boardNo) {
        BoardDto boardDto = boardMapper.selectNoticeDetail(boardNo);
        return boardDto;
    }

    public int selectNoticeTopTotal() {
        int result = boardMapper.selectNoticeTopTotal();
        return result;
    }

    public Object selectPostingDetail(Integer boardNo) {
        BoardDto boardDto = boardMapper.selectPostingDetailForEmp(boardNo);
        return boardDto;
    }

    @Transactional
    public Object insertNotice(BoardDto boardDto){
        int result = boardMapper.insertNotice(boardDto);
        System.out.println("================"+boardDto);
        return (result > 0) ? "공지사항등록성공!!!!!!" : "공지사항등록실패ㅠㅠ";
    }
    @Transactional
    public Object insertPosting(BoardDto boardDto) {
        int result = boardMapper.insertPosting(boardDto);
        System.out.println(boardDto);
        return (result > 0) ? "게시글등록성공!!":"게시글등록실패ㅠㅠ";
    }

    @Transactional
    public Object updateBoardAll(BoardDto boardDto) {
        int result = boardMapper.updateBoardAll(boardDto);
        System.out.println("업데이트 시작"+boardDto);
        return (result > 0)? "공지사항&게시글 수정 완료!!":"공지사항&게시글 수정 실패ㅜㅠ";
    }

    @Transactional
    public Object updatePosting(BoardDto boardDto) {
        int result = boardMapper.updatePosting(boardDto);
        System.out.println("업데이트 시작"+boardDto);
        return (result > 0)? "게시글 수정 완료!!":"게시글 수정 실패ㅜㅠ";
    }

    @Transactional
    public Object deleteBoardForAdmin(Integer boardNo) {
        int result = boardMapper.deleteBoardForAdmin(boardNo);
        return (result >0)? "공지사항 or 게시글 삭제 성공!!" : "공지사항 or 게시글 삭제 실패!!";
    }

    @Transactional
    public Object deleteBoardForEmp(int empNo, Integer boardNo) {
        int result = boardMapper.deleteBoardForEmp(empNo,boardNo);
        return (result >0)? "게시글 삭제 성공!!" : "게시글 삭제 실패!!";
    }
}
