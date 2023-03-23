package com.loung.semof.approval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @파일이름 : ApprovalDTO.java
 * @프로젝트 : semoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 박유리
 * @클래스설명 : 설명을 여기에 작성한다.
 */
@NoArgsConstructor
@Data
public class ApprovalDTO {
    private Integer approvNo;
    private String approvTitle;
    private Date approvDate;
    private Integer lineNo;
    private Integer empNo;
    private List<ApprovFileDTO> approvFileDTOList;
    private List <ApprovContentDTO> approvContentDTOList;
}
