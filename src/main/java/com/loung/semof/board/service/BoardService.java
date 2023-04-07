package com.loung.semof.board.service;

import com.loung.semof.board.dao.BoardMapper;
import com.loung.semof.board.dto.BoardDto;
import com.loung.semof.common.paging.SelectCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @파일이름 : BoardService.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-05
 * @작성자 : 이지형
 * @클래스설명 : 공지사항 및 게시글 crud를 위한 service.
 */
@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 공지사항에 전체 갯수를 조회.
     */
    public int selectNoticeTotal() {
        int result = boardMapper.selectNoticeTotal();
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 공지사항 조회 및 페이징.
     */
    public Object selectNoticeListWithPaging(SelectCriteria selectCriteria) {
        List<BoardDto> boardList = boardMapper.selectNoticeListWithPaging(selectCriteria);
        for(int i = 0; i < boardList.size(); i++);
        return boardList;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 게시글에 전체 갯수를 조회.
     */
    public int selectPostingTotal() {
        int result = boardMapper.selectPostingTotal();
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 게시글 조회 및 페이징.
     */
    public Object selectPostingListWithPaging(SelectCriteria selectCriteria) {
        List<BoardDto> boardList = boardMapper.selectPostingListWithPaging(selectCriteria);
        for(int i = 0; i < boardList.size(); i++);
        return boardList;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 공지사항에 최신순 3개 조회.
     */
    public int selectNoticeTopTotal() {
        int result = boardMapper.selectNoticeTopTotal();
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 공지사항 상세 조회.
     */
    public Object selectNoticeDetail(Integer boardNo) {
        BoardDto boardDto = boardMapper.selectNoticeDetail(boardNo);
        return boardDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 게시글 상세 조회.
     */
    public Object selectPostingDetail(Integer boardNo) {
        BoardDto boardDto = boardMapper.selectPostingDetailForEmp(boardNo);
        return boardDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 공지사항&게시글 등록(관리자).
     */
    @Transactional
    public Object insertAllBoardForAdmin(BoardDto boardDto){
        int result = boardMapper.insertAllBoard(boardDto);
        System.out.println("================"+boardDto);
        return (result > 0) ? "공지사항&게시글 등록 성공!" : "공지사항&게시글 등록실패...";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 게시글 등록(회원).
     */
    @Transactional
    public Object insertPosting(BoardDto boardDto) {
        int result = boardMapper.insertPosting(boardDto);
        System.out.println("================"+boardDto);
        return (result > 0) ? "게시글등록성공!!":"게시글등록실패ㅠㅠ";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 공지사항&게시글 등록(관리자).
     */
    @Transactional
    public Object updateBoardAll(BoardDto boardDto) {
        int result = boardMapper.updateBoardAll(boardDto);
        System.out.println("업데이트 시작"+boardDto);
        return (result > 0)? "공지사항&게시글 수정 완료!!":"공지사항&게시글 수정 실패ㅜㅠ";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 게시글 등록(관리자).
     */
    @Transactional
    public Object updatePosting(BoardDto boardDto) {
        int result = boardMapper.updatePosting(boardDto);
        System.out.println("업데이트 시작"+boardDto);
        return (result > 0)? "게시글 수정 완료!!":"게시글 수정 실패ㅜㅠ";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 공지사항&게시글 삭제(관리자).
     */
    @Transactional
    public Object deleteBoardForAdmin(Integer boardNo) {
        int result = boardMapper.deleteBoardForAdmin(boardNo);
        return (result >0)? "공지사항&게시글 삭제 성공!!" : "공지사항&게시글 삭제 실패!!";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 게시글 삭제(회원).
     */
    @Transactional
    public Object deleteBoardForEmp(int empNo, Integer boardNo) {
        int result = boardMapper.deleteBoardForEmp(empNo,boardNo);
        return (result >0)? "게시글 삭제 성공!!" : "게시글 삭제 실패!!";
    }
}
