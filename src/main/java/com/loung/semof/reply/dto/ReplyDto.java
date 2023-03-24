package com.loung.semof.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReplyDto {

    private Integer replyCode;
    private String replyContent;

}
