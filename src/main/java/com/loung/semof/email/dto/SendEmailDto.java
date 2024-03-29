package com.loung.semof.email.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.loung.semof.common.dto.EmployeeDto;
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
public class SendEmailDto extends EmailDto {
    public SendEmailDto(Long mailNo, Long empNo, Long emailFileNo, String receiverAddr, String senderName, String senderAddr, String title, String content, LocalDateTime sendDate, String tempStatus, List<EmailAttachDto> emailAttachDtoList, String status, String category) {
        this.mailNo = mailNo;
        this.empNo = empNo;
        this.emailFileNo = emailFileNo;
        this.receiverAddr = receiverAddr;
        this.senderName = senderName;
        this.senderAddr = senderAddr;
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
        this.tempStatus = tempStatus;
        this.emailAttachDtoList = emailAttachDtoList;
        this.status = status;
        this.category = category;
    }

    public SendEmailDto(Long emailFileNo, String senderName, String title, String content, LocalDateTime sendDate, String status, String category, Long mailNo, Long empNo, Long emailFileNo1, String receiverAddr, String senderName1, String senderAddr, String title1, String content1, LocalDateTime sendDate1, String tempStatus, List<EmailAttachDto> emailAttachDtoList, String status1, String category1) {
        super(emailFileNo, senderName, title, content, sendDate, status, category);
        this.mailNo = mailNo;
        this.empNo = empNo;
        this.emailFileNo = emailFileNo1;
        this.receiverAddr = receiverAddr;
        this.senderName = senderName1;
        this.senderAddr = senderAddr;
        this.title = title1;
        this.content = content1;
        this.sendDate = sendDate1;
        this.tempStatus = tempStatus;
        this.emailAttachDtoList = emailAttachDtoList;
        this.status = status1;
        this.category = category1;
    }

    private Long mailNo;    //이메일번호
    private Long empNo; //사원번호
    private Long emailFileNo;   //이메일파일번호
    private String receiverAddr;    //수신자이메일주소
    private String senderName; // 발신자 이름
    private String senderAddr; // 발신자 이메일주소
    private String title;   //이메일제목
    private String content; //이메일내용
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime sendDate; //이메일발송날짜
    private String tempStatus;  //임시저장여부
    private List<EmailAttachDto> emailAttachDtoList; // 첨부파일 리스트
    private String status;  //삭제 여부
    private String category; //이메일카테고리

    // emailAttachDtoList 필드의 getter, setter
    public List<EmailAttachDto> getEmailAttachDtoList() {
        return emailAttachDtoList;
    }

    public void setEmailAttachDtoList(List<EmailAttachDto> emailAttachDtoList) {
        this.emailAttachDtoList = emailAttachDtoList;
    }

    public void setSenderInfo(EmployeeDto employee) {
        this.senderName = employee.getEmpName();
        this.senderAddr = employee.getEmail();
    }
}
