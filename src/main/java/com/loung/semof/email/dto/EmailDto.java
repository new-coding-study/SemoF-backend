package com.loung.semof.email.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

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
