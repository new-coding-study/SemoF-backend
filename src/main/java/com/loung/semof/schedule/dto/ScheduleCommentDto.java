package com.loung.semof.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 파일이름 : ScheduleCommentDto
 *
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023/04/09
 * @작성자 : 박지희
 * @클래스설명 : 설명을 여기에 작성한다.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCommentDto {
    private Long comNo;
    private String scdNo;
    private Long comWriter;
    private String comContent;
    private String comWriteTime;
    private String comWriterEmpName;
}
