package com.loung.semof.common.dto;

import lombok.*;

/**
 * @파일이름 : BranchDto.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : 모델(Model)과 뷰(View) 사이에서 데이터 전송을 담당하는 클래스
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {
    
    private Long branchCode; //지점코드
    private String branchName;   //지점명
}
