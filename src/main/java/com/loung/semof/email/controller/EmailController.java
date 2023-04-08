package com.loung.semof.email.controller;

import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.email.config.EmailConfig;
import com.loung.semof.email.dao.EmailMapper;
import com.loung.semof.email.dto.EmailAttachDto;
import com.loung.semof.email.dto.EmailDto;
import com.loung.semof.email.dto.ReceiveEmailDto;
import com.loung.semof.email.dto.SendEmailDto;
import com.loung.semof.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


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
    public ResponseEntity<?> sendEmail(@ModelAttribute SendEmailDto emailDto,
                                       @RequestParam(value = "file", required = false) MultipartFile file) {

        // 사원 정보 조회
        EmployeeDto sender = emailService.getEmployee(emailDto.getEmpNo());
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

        // 첨부 파일 저장
        List<EmailAttachDto> emailAttachDtoList = new ArrayList<>();
        if (file != null && !file.isEmpty()) {
            EmailAttachDto emailAttachDto = EmailAttachDto.builder()
                    .originName(file.getOriginalFilename())
                    .changeName(UUID.randomUUID().toString())
                    .uploadDate(LocalDateTime.now())
                    .build();

            byte[] fileContent;
            try {
                fileContent = file.getBytes();
            } catch (IOException e) {
                throw new RuntimeException("첨부 파일을 읽는 중 예외가 발생했습니다.", e);
            }
            emailAttachDto.setFileData(fileContent);

            emailAttachDtoList.add(emailAttachDto);
        }

        // 이메일 정보 설정
        emailDto.setEmailAttachDtoList(emailAttachDtoList);

        emailDto.setSenderName(sender.getEmpName());

        emailDto.setSenderAddr(senderAddr);

        emailDto.setTempStatus("N");

        // 이메일 발송 및 저장
        try {
            emailService.insertSendEmail(emailDto, file);
        } catch (Exception e) {
            throw new RuntimeException("이메일을 보내는 중 예외가 발생했습니다.", e);
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"success\":true}");

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
     * @파일이름 : EmailController.java
     * @프로젝트 : SemoF
     * @버전관리 : 1.0.0
     * @작성일 : 2023-04-04
     * @작성자 : 이현도
     * @클래스설명 : 발신 이메일 전체를 조회해오는 메소드
     */
    @GetMapping("/send/list")
    public ResponseEntity<ResponseDto> selectSendEmailListWithPaging(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo) throws SQLException {

        try {
            int totalCount = emailService.selectEmailListTotal();

            int limit = 10;

            int buttonAmount = 5;

            SelectCriteria selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);

            List<SendEmailDto> sendEmails = emailService.selectSendEmailListWithPaging(selectCriteria.getStartRow(), selectCriteria.getEndRow());

            ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();

            responseDtoWithPaging.setPageInfo(selectCriteria);

            responseDtoWithPaging.setData(sendEmails);

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

        } catch (SQLException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null));
        }
    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 발신 이메일을 번호에 따라 조회해오는 메소드
     */
    @GetMapping("/send/{mailNo}")
    public ResponseEntity<ResponseDto> selectSendEmail(@PathVariable("mailNo") Long mailNo) {

        try {
            SendEmailDto email = emailService.selectSendEmail(mailNo);

            return ResponseEntity.ok()
                    .body(new ResponseDto(HttpStatus.OK, "정상 확인", email));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 조회에 실패했습니다.", null));
        }
    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 수신메일함을 조회하는 메소드
     */
    @GetMapping("/lists")
    public ResponseEntity<ResponseDto> selectEmailListWithPaging(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo) throws SQLException {

        emailService.fetchEmailsFromGmailAndStore();

        List<ReceiveEmailDto> receiveList = Collections.emptyList();

        try {
            int totalCount = emailService.selectReceiveEmailTotal();

            log.info("[EmailContorller] totalCount = " + totalCount);

            int limit = 10;

            int buttonAmount = 5;

            SelectCriteria selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);

            List<ReceiveEmailDto> receiveEmails = emailService.selectEmailListWithPaging(selectCriteria.getStartRow(), selectCriteria.getEndRow());

            ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();

            responseDtoWithPaging.setPageInfo(selectCriteria);

            responseDtoWithPaging.setData(receiveEmails);

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

        } catch (SQLException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null));
        }
    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 수신 이메일을 번호에 따라 조회해오는 메소드
     */
    @GetMapping("/receive/{receiveNo}")
    public ResponseEntity<ResponseDto> selectReceiveEmail(@PathVariable("receiveNo") Long receiveNo) {

        try {
            ReceiveEmailDto email = emailService.selectReceiveEmail(receiveNo);

            return ResponseEntity.ok()
                    .body(new ResponseDto(HttpStatus.OK, "정상 확인", email));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 조회에 실패했습니다.", null));
        }
    }

    /**
     * @작성일 : 2023-04-06
     * @작성자 : 이현도
     * @메소드설명 :  상태를 바꿔서 휴지통으로 이동시키는 메소드
     */
    @PutMapping("/{mailNo}/{category}")
    public ResponseEntity<ResponseDto> updateToTrash(@PathVariable Long mailNo, @PathVariable String category) {

        log.info("[EmailController]  mailNo :  " + mailNo);

        log.info("[EmailController]  category :  " + category);

        String result = emailService.updateToTrash(mailNo, category);

        if (result.equals("삭제 성공")) {
            return ResponseEntity.ok()
                    .body(new ResponseDto(HttpStatus.OK, "성공적으로 삭제되었습니다.",  result));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "삭제 실패", null));
        }
    }

    @GetMapping("/deleted")
    public ResponseEntity<ResponseDto> selectTrashEmailListWithPaging(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo) throws SQLException {
        try {
            int sendCount = emailService.selectTrashSendListTotal();
            int receiveCount = emailService.selectTrashReceiveListTotal();
            int totalCount = sendCount + receiveCount;
            int limit = 10;
            int buttonAmount = 5;

            SelectCriteria selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);

            List<EmailDto> deleteEmails = emailService.selectTrashEmailListWithPaging(selectCriteria.getStartRow(), selectCriteria.getEndRow());

            List<EmailDto> deleteEmailsDtoList = deleteEmails.stream() // 가져온 이메일 리스트를 stream() 메소드를 사용해 스트림으로 변환
                    .map(emailDto -> new EmailDto( // map() 메소드를 사용해 스트림의 각 요소에 대해 EmailDto 객체로 매핑(람다식 사용)
                            emailDto.getEmailFileNo(),
                            emailDto.getSenderName(),
                            emailDto.getTitle(),
                            emailDto.getContent(),
                            emailDto.getSendDate(),
                            emailDto.getStatus(),
                            emailDto.getCategory()
                    ))
                    .collect(Collectors.toList()); // collect() 메소드를 사용해 매핑된 객체들을 리스트로 반환

            ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();

            responseDtoWithPaging.setPageInfo(selectCriteria);

            responseDtoWithPaging.setData(deleteEmailsDtoList);

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null));
        }
    }

}
