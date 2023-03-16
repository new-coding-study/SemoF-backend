package com.loung.semof.email.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDto {

    private int mailNo; // 이메일 번호 PK
    private int empNo; // 사원 번호 FK
    private int eFileNo; // 이메일 파일번호 FK
    private String receiverAddr; // 수신자 이메일 주소
    private String title; // 이메일 제목
    private String content; // 이메일 본문
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime sendDate; // 이메일 발송 날짜
    private String tempStatus; // 임시 저장 여부

}
