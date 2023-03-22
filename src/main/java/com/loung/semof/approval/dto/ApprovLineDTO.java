package com.loung.semof.approval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ApprovLineDTO {
    private Integer lineNo;
    private String lineName;
    private String empName;
    private Integer empNo;
    private String bName;
    private Integer bCode;
//    private String line;
    private List<ApprovOrderDTO> approvOrderDTOList;
}
