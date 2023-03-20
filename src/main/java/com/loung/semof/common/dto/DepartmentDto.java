package com.loung.semof.common.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    
    private String deptCode;    //부서코드
    private String deptName; //부서명
}
