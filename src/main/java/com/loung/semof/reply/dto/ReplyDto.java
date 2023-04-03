package com.loung.semof.reply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class ReplyDto {

    private Integer rowNum;
    private Integer boardNo;
    private String empName;
    private Integer replyCode;
    private String replyContent;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate replyWriteDate;
    private Integer empNo;

//
}
