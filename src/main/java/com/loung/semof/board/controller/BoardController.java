package com.loung.semof.board.controller;

import com.loung.semof.board.service.BoardService;
import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board-lists")
    public ResponseEntity<ResponseDto> selectBoardListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset){
        System.out.println("selectBoardListWithPaging execute" + offset);

        int totalCount = boardService.selectNoticeTotal();
        int limit = 10;
        int buttonAmount = 13;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        System.out.println("selectCriteria=========execute" + selectCriteria);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(boardService.selectNoticeListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공!!!", responseDtoWithPaging));


    }


}
