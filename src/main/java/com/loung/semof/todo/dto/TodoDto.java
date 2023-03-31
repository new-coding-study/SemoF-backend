package com.loung.semof.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
    private Long todoNo;
    private String todoName;
//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private String todoDate;
//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="HH:mm:ss", timezone="Asia/Seoul")
    private String todoTime;
    private String todoContent;
    private int todoFinish;
    private int todoStar;

    // 할 일 카테고리 테이블
    private Long cateNo; // 외래키
    private String cateName;
    private String cateColor;
    private Long empNo;
//
}
