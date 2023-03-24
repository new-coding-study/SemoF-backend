//package com.loung.semof.email.dto;
//
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
///**
// * @파일이름 : EmailDto.java
// * @프로젝트 : SemoF
// * @버전관리 : 1.0.0
// * @작성일 : 2023-03-23
// * @작성자 : 이현도
// * @클래스설명 : 모델(Model)과 뷰(View) 사이에서 데이터 전송을 담당하는 클래스
// */
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class ReceiveEmailDto extends EmailAttachDto {
//        public ReceiveEmailDto(Long emailFileNo, String originName, String changeName, String filePath, LocalDateTime uploadDate, byte[] fileData, Long receiveNo, String receiverAddr, String senderName, String title, String content, LocalDateTime sendDate, List<EmailAttachDto> attachList) {
//                super(emailFileNo, originName, changeName, filePath, uploadDate);
//                this.receiveNo = receiveNo;
//                this.receiverAddr = receiverAddr;
//                this.senderName = senderName;
//                this.title = title;
//                this.content = content;
//                this.sendDate = sendDate;
//                this.attachList = attachList;
//        }
//        private Long receiveNo; //수신이메일번호
//        private String receiverAddr;  //수신자 이메일 주소
//        private String senderName;    //발신자 이름
//        private String title;         //이메일 제목
//        private String content;       //이메일 내용
//        private LocalDateTime sendDate;  //이메일 전송 일시
//        private List<EmailAttachDto> attachList;  //첨부 파일 목록
//}
