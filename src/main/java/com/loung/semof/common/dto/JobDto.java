package com.loung.semof.common.dto;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private Long jobCode;   //직급코드
    private String jobName; //직급명

}
