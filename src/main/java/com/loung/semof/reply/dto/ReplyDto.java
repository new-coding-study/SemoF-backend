package com.loung.semof.reply.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ReplyDto {

    private Integer replyCode;
    private String replyContent;
    private Integer boardNo;
    private Integer empNo;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate replyWriteDate;
    private String empName;

    private Integer rowNum;




//
}
