package com.loung.semof.email.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @파일이름 : EmailAttachDto.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-23
 * @작성자 : 이현도
 * @클래스설명 : 모델(Model)과 뷰(View) 사이에서 데이터 전송을 담당하는 클래스
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailAttachDto {
    private Long emailFileNo;   //이메일파일번호
    private String originName; //이메일파일원본명
    private String changeName;  //이메일파일수정명
    private String filePath; //이메일파일경로
    private LocalDateTime uploadDate;   //이메일파일업로드일
//    private byte[] fileData; // 첨부파일 데이터

}
