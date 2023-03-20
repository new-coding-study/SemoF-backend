package com.loung.semof.common.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {
    
    private Long bCode; //지점코드
    private String bName;   //지점명
}
