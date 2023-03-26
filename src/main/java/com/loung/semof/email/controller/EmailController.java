package com.loung.semof.email.controller;

import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.email.config.EmailConfig;
import com.loung.semof.email.dao.EmailMapper;
import com.loung.semof.email.dto.EmailAttachDto;
import com.loung.semof.email.dto.ReceiveEmailDto;
import com.loung.semof.email.dto.SendEmailDto;
import com.loung.semof.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @파일이름 : EmailController.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-23
 * @작성자 : 이현도
 * @클래스설명 : 이메일과 관련된 뷰의 명령을 실행하는 클래스
 */
@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;
    private final EmailConfig emailConfig;

    private final EmailMapper emailMapper;

    public EmailController(EmailService emailService, EmailConfig emailConfig, EmailMapper emailMapper) {
        this.emailService = emailService;
        this.emailConfig = emailConfig;
        this.emailMapper = emailMapper;
    }

    /**
     * @작성일 : 2023-03-24
     * @작성자 : 이현도
     * @메소드설명 : 이메일 발송과 관련된 기능을 수행하는 메소드
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestParam("empNo") Long empNo,
            @RequestParam(value = "attachments", required = false) List<MultipartFile> attachments,
                                           @ModelAttribute SendEmailDto emailDto /*,
                                           HttpServletRequest request */) throws MessagingException {

        // 현재 로그인한 사용자의 사원번호 가져오기 - 로그인 기능 구현 후 사용하기
//        Long empNo = (Long) request.getSession().getAttribute("empNo");

//        if (empNo == null) {
//            throw new IllegalArgumentException("Employee number must not be null");
//        }

        // 사원 정보 조회
        EmployeeDto sender = emailService.getEmployee(empNo);

        if (sender == null || sender.getEmail() == null) {
            throw new RuntimeException("발신자의 이메일 주소를 찾을 수 없습니다.");
        }

        // 발신자 이메일 주소 설정
        String senderAddr = sender.getEmail();

        if (senderAddr == null || senderAddr.trim().isEmpty()) {
            throw new IllegalArgumentException("발신자의 이메일 주소는 비어 있을 수 없습니다.");
        }

        // 수신자 이메일 주소 설정
        String receiverAddr = emailDto.getReceiverAddr();

        if (receiverAddr == null || receiverAddr.trim().isEmpty()) {
            throw new IllegalArgumentException("수신자의 이메일 주소는 비어 있을 수 없습니다.");
        }

        List<EmailAttachDto> emailAttachDtoList = new ArrayList<>();

        if (attachments != null && !attachments.isEmpty()) {
            for (MultipartFile attachment : attachments) {
                EmailAttachDto emailAttachDto = EmailAttachDto.builder()
                        .originName(attachment.getOriginalFilename())
                        .changeName(UUID.randomUUID().toString())
                        .filePath("/attachments/")
                        .uploadDate(LocalDateTime.now())
                        .build();

                emailAttachDtoList.add(emailAttachDto);

                String filePath = emailAttachDto.getFilePath();

                String fileName = emailAttachDto.getChangeName();

                try {
                    attachment.transferTo(new File(filePath + fileName));

                } catch (IOException e) {
                    throw new RuntimeException("파일 업로드 중 예외가 발생했습니다.");
                }
            }
        }

        emailDto.setEmailAttachDtoList(emailAttachDtoList);

        // 발신자 정보 설정
        emailDto.setSenderName(sender.getEmpName());

        emailDto.setSenderAddr(senderAddr);

        try {
            emailService.insertSendEmail(emailDto);

        } catch (Exception e) {
            log.error("이메일을 보내는 중 예외가 발생했습니다.", e);

            throw new RuntimeException("이메일을 보내는 중 예외가 발생했습니다.");
        }

        return ResponseEntity.ok().build();
    }

    /**
     * @작성일 : 2023-03-24
     * @작성자 : 이현도
     * @메소드설명 : 임시저장여부(TempStatus)의 상태값이 'Y'인 메일을 불러오는 메소드
     */
    @GetMapping("/temp")
    public ResponseEntity<List<SendEmailDto>> getTempEmails() {
        List<SendEmailDto> emails = emailMapper.selectByTempStatus("Y");
        return ResponseEntity.ok(emails);
    }


    /**
     * @작성일 : 2023-03-24
     * @작성자 : 이현도
     * @메소드설명 : 이메일 수신함과 관련된 기능을 수행하는 메소드
     */
    @GetMapping("/list")
    public ResponseEntity<List<ReceiveEmailDto>> getMailList() {
        String host = emailConfig.getHost();
        String username = emailConfig.getUsername();
        String password = emailConfig.getPassword();

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", host);
        props.setProperty("mail.imaps.port", "993");

        List<ReceiveEmailDto> mailList = new ArrayList<>();

        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            for (Message message : messages) {
                ReceiveEmailDto receiveEmailDto = new ReceiveEmailDto();
                receiveEmailDto.setReceiverAddr(Arrays.toString(message.getRecipients(Message.RecipientType.TO)));
                receiveEmailDto.setSenderName(message.getFrom()[0].toString());
                receiveEmailDto.setTitle(message.getSubject());
                receiveEmailDto.setContent(message.getContent().toString());
                receiveEmailDto.setSendDate(LocalDateTime.ofInstant(message.getSentDate().toInstant(), ZoneId.systemDefault()));
                mailList.add(receiveEmailDto);
            }

            inbox.close(false);
            store.close();

            emailService.insertEmailList(mailList); // 받은 이메일 목록을 데이터베이스에 저장

            List<ReceiveEmailDto> emailList = emailService.selectEmailList(); // 데이터베이스에서 이메일 목록을 조회
            return new ResponseEntity<>(emailList, HttpStatus.OK);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
