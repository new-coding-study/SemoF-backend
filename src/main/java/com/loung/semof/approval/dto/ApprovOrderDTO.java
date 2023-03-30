package com.loung.semof.approval.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @파일이름 : ApprovOrderDTO.java
 * @프로젝트 : semoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-22
 * @작성자 : 박유리
 * @클래스설명 : 설명을 여기에 작성한다.
 */
@NoArgsConstructor
@Data
public class ApprovOrderDTO {
  private Integer orderNo;
  private Integer lineNo;
  private String jobName;
  private ApprovStatusDTO approvStatusDTO;
}
