package com.loung.semof.todo.dto;

import lombok.*;

import java.sql.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Long todoNo;
    private String todoName;
    private Date todoDate;
    private Date todoTime;
    private String todoContent;
    private int todoFinish;
    private int todoStar;

    // 할 일 카테고리 테이블
    private Long cateNo; // 외래키
    private String cateName;
    private String cateColor;
    private Long empNo;

}
