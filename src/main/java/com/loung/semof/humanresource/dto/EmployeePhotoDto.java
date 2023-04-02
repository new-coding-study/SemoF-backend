package com.loung.semof.humanresource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

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
