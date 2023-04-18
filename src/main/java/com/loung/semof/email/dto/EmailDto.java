package com.loung.semof.email.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @파일이름 : EmailDto.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-09
 * @작성자 : 이현도
 * @클래스설명 : 모델(Model)과 뷰(View) 사이에서 데이터 전송을 담당하는 클래스
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
public class EmailDto extends EmailAttachDto {

    public EmailDto(Long emailFileNo, String senderName, String title, String content, LocalDateTime sendDate, String status, String category) {
        this.emailFileNo = emailFileNo;
        this.senderName = senderName;
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
        this.status = status;
        this.category = category;
    }

    public EmailDto(Long emailFileNo, String originName, String changeName, String filePath, LocalDateTime uploadDate, byte[] fileData, Long emailFileNo1, String senderName, String title, String content, LocalDateTime sendDate, String status, String category) {
        super(emailFileNo, originName, changeName, filePath, uploadDate, fileData);
        this.emailFileNo = emailFileNo1;
        this.senderName = senderName;
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
        this.status = status;
        this.category = category;
    }

    private Long emailFileNo;   //이메일파일번호
    private String senderName; // 발신자 이름
    private String title;   //이메일제목
    private String content; //이메일내용
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime sendDate; //이메일발송날짜
    private String status;  //삭제 여부
    private String category; //이메일카테고리

}
