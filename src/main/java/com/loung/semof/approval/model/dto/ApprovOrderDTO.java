package com.loung.semof.approval.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ApprovOrderDTO {
  private Integer orderNo;
  private Integer lineNo;
  private String jobName;
  private ApprovStatusDTO approvStatusDTO;
}
