package com.loung.semof.approval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ApprovStatusDTO {
    private char statusCode;
    private String statusName;
    private Integer orderNo;
    private Integer approvNo;
}
