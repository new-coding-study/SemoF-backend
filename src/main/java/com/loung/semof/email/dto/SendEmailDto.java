package com.loung.semof.email.dto;

import com.loung.semof.common.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailDto extends EmailAttachDto {
    public SendEmailDto(Long emailFileNo, String originName, String changeName, String filePath, LocalDateTime uploadDate, Long mailNo, Long empNo, Long emailFileNo1, String receiverAddr, String senderName, String senderAddr, String title, String content, LocalDateTime sendDate, String tempStatus, List<EmailAttachDto> emailAttachDtoList) {
        super(emailFileNo, originName, changeName, filePath, uploadDate);
        this.mailNo = mailNo;
        this.empNo = empNo;
        this.emailFileNo = emailFileNo1;
        this.receiverAddr = receiverAddr;
        this.senderName = senderName;
        this.senderAddr = senderAddr;
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
        this.tempStatus = tempStatus;
        this.emailAttachDtoList = emailAttachDtoList;
    }

    private Long mailNo;    //이메일번호
    private Long empNo; //사원번호
    private Long emailFileNo;   //이메일파일번호
    private String receiverAddr;    //수신자이메일주소
    private String senderName; // 발신자 이름
    private String senderAddr; // 발신자 이메일주소
    private String title;   //이메일제목
    private String content; //이메일내용
    private LocalDateTime sendDate; //이메일발송날짜
    private String tempStatus;  //임시저장여부
    private List<EmailAttachDto> emailAttachDtoList; // 첨부파일 리스트
    private String status;  //삭제 여부

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
