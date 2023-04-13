package com.loung.semof.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @파일이름 : BoardDto.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이지형
 * @클래스설명 : 공지사항 및 게시글 위한 DTO .
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardDto {
    private Integer boardNo;
    private int empNo;
    private String empName;
    private String boardTitle;
    private String boardContent;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate writeDate;
    private int boardCateCode;
    private Long rowNum;
}
