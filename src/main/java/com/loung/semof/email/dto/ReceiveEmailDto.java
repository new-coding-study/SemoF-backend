package com.loung.semof.email.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @파일이름 : EmailDto.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-23
 * @작성자 : 이현도
 * @클래스설명 : 모델(Model)과 뷰(View) 사이에서 데이터 전송을 담당하는 클래스
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ReceiveEmailDto extends EmailDto {
        public ReceiveEmailDto(Long receiveNo, String receiverAddr, String senderName, String title, String content, LocalDateTime sendDate, List<EmailAttachDto> attachList, String status, String isRead, String category) {
                this.receiveNo = receiveNo;
                this.receiverAddr = receiverAddr;
                this.senderName = senderName;
                this.title = title;
                this.content = content;
                this.sendDate = sendDate;
                this.attachList = attachList;
                this.status = status;
                this.isRead = isRead;
                this.category = category;
        }

        public ReceiveEmailDto(Long emailFileNo, String senderName, String title, String content, LocalDateTime sendDate, String status, String category, Long receiveNo, String receiverAddr, String senderName1, String title1, String content1, LocalDateTime sendDate1, List<EmailAttachDto> attachList, String status1, String isRead, String category1) {
                super(emailFileNo, senderName, title, content, sendDate, status, category);
                this.receiveNo = receiveNo;
                this.receiverAddr = receiverAddr;
                this.senderName = senderName1;
                this.title = title1;
                this.content = content1;
                this.sendDate = sendDate1;
                this.attachList = attachList;
                this.status = status1;
                this.isRead = isRead;
                this.category = category1;
        }

        private Long receiveNo; //수신이메일번호
        private String receiverAddr;  //수신자 이메일 주소
        private String senderName;    //발신자 이름
        private String title;         //이메일 제목
        private String content;       //이메일 내용
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private LocalDateTime sendDate;  //이메일 전송 일시
        private List<EmailAttachDto> attachList;  //첨부 파일 목록
        private String status;  //삭제 여부
        private String isRead;  //읽음 여부
        private String category; //이메일카테고리
}
