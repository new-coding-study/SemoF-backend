package com.loung.semof.reply.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.reply.dao.ReplyMapper;
import com.loung.semof.reply.dto.ReplyDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @파일이름 : ReplyService.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-05
 * @작성자 : 이지형
 * @클래스설명 : 댓글 crud를 위한 service.
 */
@Service
public class ReplyService {
    private final ReplyMapper replyMapper;
    public ReplyService(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 전체의 갯수를 조회.
     */
    public int selectReplyTotal(int boardNo){
        int result = replyMapper.selectReplyTotal(boardNo);
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 조회 및 페이징.
     */
    public Object selectReplyWithPaging(SelectCriteria selectCriteria, int boardNo){
        List<ReplyDto> replyList = replyMapper.selectReplyWithPaging(selectCriteria, boardNo);
        return replyList;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 등록.
     */
    @Transactional
    public Object insertReply(ReplyDto replyDto) {
        System.out.println("replyDto = " + replyDto);
        int result = replyMapper.insertReply(replyDto);
        return (result > 0)? "댓글추가 성공" : "댓글추가 실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 삭제(관리자).
     */
    @Transactional
    public Object deleteForAdmin(int boardNo, int replyCode) {
        int result = replyMapper.deleteForAdmin(boardNo, replyCode);
        return (result > 0)? "댓글 삭제 완료" : "댓글 삭제 완료";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 삭제(회원).
     */
    @Transactional
    public Object deleteForEmp(int empNo, int replyCode) {
        int result = replyMapper.deleteForEmp(empNo,replyCode);
        return (result > 0)? "댓글 삭제 완료" : "댓글 삭제 완료";
    }


    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 댓글 수정(일단 보류).
     */
//    public Object updateReply(ReplyDto replyDto) {
//        int result = replyMapper.updateReply(replyDto);
//        return (result > 0)? "댓글 수정완료":"댓글 수정 실패";
//    }

}
