package com.loung.semof.humanresource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @파일이름 : EmployeePhotoDto.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-26
 * @작성자 : 이현도
 * @클래스설명 : 모델(Model)과 뷰(View) 사이에서 데이터 전송을 담당하는 클래스
 *  */
@Builder
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePhotoDto {
    private Long photoNo;   // 사진번호
    private Long empNo; // 사원번호
    private String filePath;    //증명사진경로
    private String originName;  //증명사진원본명
    private String changeName;  //증명사진수정명
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime uploadDate;   //증명사진업로드일
}
