package com.loung.semof.board.controller;

import com.loung.semof.board.dto.BoardDto;
import com.loung.semof.board.service.BoardService;
import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @파일이름 : BoardController.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이지형
 * @클래스설명 : 설명을 여기에 작성한다.
 */

@RestController
@RequestMapping("/boards")
public class BoardController {
//
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * @작성일 : 2023.03.23
     * @작성자 : 이지형
     * @메소드설명 : 공지사항에 대한 페이링 처리 및 리스트를 불러오기 위한 메소드
     */
    @GetMapping("/board-notice-lists")
    public ResponseEntity<ResponseDto> selectNoticeListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){
        System.out.println("selectNoticeListWithPaging execute" + offset);

        int totalCount = boardService.selectNoticeTotal();
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        System.out.println("selectCriteria=========execute" + selectCriteria);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(boardService.selectNoticeListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공!!!", responseDtoWithPaging));
    }

    @GetMapping("/board-notice-top3")
    public ResponseEntity<ResponseDto> selectNoticeTop3WithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){
        System.out.println("selectNoticeListWithPaging execute" + offset);

        int totalCount = boardService.selectNoticeTopTotal();
        int limit = 3;
        int buttonAmount = 0;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        System.out.println("selectCriteria=========execute" + selectCriteria);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(boardService.selectNoticeListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공!!!", responseDtoWithPaging));
    }

    @GetMapping("/board-notice/{boardNo}")
    public ResponseEntity<ResponseDto> selectNoticeDetail(@PathVariable Integer boardNo){
        System.out.println("boardNo : "+boardNo);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "공지사항 상세조회 성공", boardService.selectNoticeDetail(boardNo)));
    }

    @GetMapping("/board-posting-lists")
    public ResponseEntity<ResponseDto> selectPostingListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){
        System.out.println("selectPostingListWithPaging execute" + offset);

        int totalCount = boardService.selectPostingTotal();
        int limit = 10;
        int buttonAmount = 13;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        System.out.println("selectCriteria=========execute" + selectCriteria);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(boardService.selectPostingListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공!!!", responseDtoWithPaging));
    }

    @GetMapping("/board-posting-lists/{boardNo}")
    public ResponseEntity<ResponseDto> selectPostingDetail(@PathVariable Integer boardNo){
        System.out.println("boardNo : "+boardNo);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "게시글 상세조회 성공", boardService.selectPostingDetail(boardNo)));
    }

    @PostMapping("/boardNotice-lists")
    public ResponseEntity<ResponseDto> insertNotice(@ModelAttribute BoardDto boardDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "공지사항등록 성공!!!", boardService.insertNotice(boardDto)));
    }

    @PostMapping("/board-posting-lists")
    public ResponseEntity<ResponseDto> insertPosting(@ModelAttribute BoardDto boardDto){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "게시글 등록 성공!!!", boardService.insertPosting(boardDto)));
    }

    @PutMapping("/board-all-lists/{boardNo}")
    public ResponseEntity<ResponseDto> updateBoardForAdmin(@ModelAttribute BoardDto boardDto, @PathVariable Integer boardNo) {
        System.out.println("============공지사항&게시물 업데이트======="+ boardDto);
        boardDto.setBoardNo(boardNo);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"공지사항&게시물 업데이트 성공!!!", boardService.updateBoardAll(boardDto)));
    }

    @PutMapping("/board-posting-lists/{boardNo}")
    public ResponseEntity<ResponseDto> updatePosting(@ModelAttribute BoardDto boardDto, @PathVariable Integer boardNo) {
        System.out.println("============게시판 업데이트======="+ boardDto);
        boardDto.setBoardNo(boardNo);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"게시글 업데이트 성공!!!", boardService.updatePosting(boardDto)));
    }

    @DeleteMapping("/board-all-lists/{boardNo}")
    public ResponseEntity<ResponseDto> deleteBoardForAdmin(@PathVariable Integer boardNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"공지사항 or 게시물삭제", boardService.deleteBoardForAdmin(boardNo)));
    }
    @DeleteMapping("/board-posting-lists/{empNo}/{boardNo}")
    public ResponseEntity<ResponseDto> deleteBoardForAdmin(@PathVariable int empNo, @PathVariable Integer boardNo){
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"게시물삭제", boardService.deleteBoardForEmp(empNo,boardNo)));
    }
}
