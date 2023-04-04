package com.loung.semof.email.service;

import com.loung.semof.common.dao.EmployeeMapper;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.email.dao.EmailMapper;
import com.loung.semof.email.dto.EmailAttachDto;
import com.loung.semof.email.dto.ReceiveEmailDto;
import com.loung.semof.email.dto.SendEmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @파일이름 : EmailService.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-23
 * @작성자 : 이현도
 * @클래스설명 : EmailController에서 요구하는 비즈니스 로직을 수행하는 클래스
 */
@Slf4j
@Service
public class EmailService {

    private final EmailMapper emailMapper;
    private final JavaMailSender javaMailSender;
    private final EmployeeMapper employeeMapper;

    public EmailService(EmailMapper emailMapper, JavaMailSender javaMailSender, EmployeeMapper employeeMapper) {
        this.emailMapper = emailMapper;
        this.javaMailSender = javaMailSender;
        this.employeeMapper = employeeMapper;
    }

    /**
     * @작성일 : 2023-03-23
     * @작성자 : 이현도
     * @메소드설명 : empNo로 사원 정보를 조회해서 반환하는 메소드
     */
    public EmployeeDto getEmployee(Long empNo) {

        Optional<EmployeeDto> employeeOptional = employeeMapper.selectByEmpNo(empNo);

        if (employeeOptional.isPresent()) {
            EmployeeDto employee = employeeOptional.get();

            return EmployeeDto.builder()
                    .empNo(employee.getEmpNo())
                    .empName(employee.getEmpName())
                    .empReg(employee.getEmpReg())
                    .email(employee.getEmail())
                    .phone(employee.getPhone())
                    .address(employee.getAddress())
                    .salary(employee.getSalary())
                    .enrollDate(employee.getEnrollDate())
                    .retireDate(employee.getRetireDate())
                    .workStatus(employee.getWorkStatus())
                    .gender(employee.getGender())
                    .jobCode(employee.getJobCode())
                    .deptCode(employee.getDeptCode())
                    .branchCode(employee.getBranchCode())
                    .build();
        }
        throw new RuntimeException("사원 정보를 찾을 수 없습니다.");
    }

    /**
     * @작성일 : 2023-03-23
     * @작성자 : 이현도
     * @메소드설명 : EmailDto 객체에 저장된 정보를 사용하여 이메일을 발송하는 메소드
     */
    public void insertSendEmail(SendEmailDto emailDto) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject(emailDto.getTitle());

        helper.setText(emailDto.getContent(), true);

        helper.setFrom(emailDto.getSenderAddr(), emailDto.getSenderName());

        helper.setTo(emailDto.getReceiverAddr());

        List<EmailAttachDto> emailAttachDtoList = emailDto.getEmailAttachDtoList();

        if (emailAttachDtoList != null && !emailAttachDtoList.isEmpty()) {

            for (EmailAttachDto emailAttachDto : emailAttachDtoList) {

                File attachment = new File(emailAttachDto.getFilePath() + emailAttachDto.getChangeName());

                FileSystemResource file = new FileSystemResource(attachment);

                helper.addAttachment(emailAttachDto.getOriginName(), file);
            }
        }

        javaMailSender.send(mimeMessage);

        emailMapper.insertSendEmail(emailDto);
    }

    /**
     * @작성일 : 2023-03-24
     * @작성자 : 이현도
     * @메소드설명 : 임시 저장 여부의 갱신 기능을 구현하는 메소드
     */
//    private void updateTempStatus(String tempStatus, Long mailNo) {
//        Optional<SendEmailDto> optionalEmail = emailMapper.selectByEmailNo(mailNo);
//
//        log.info("[EmailService] optionalEmail" + optionalEmail);
//
//        if (optionalEmail.isPresent()) {
//            SendEmailDto email = optionalEmail.get();
//
//            log.info("[EmailService] email : " + email);
//
//            email.setTempStatus(tempStatus != null ? tempStatus : "N");
//
//            log.info("[EmailService] email : " + email);
//            emailMapper.insertSendEmail(email);
//        }
//    }

    /**
     * @작성일 : 2023-03-24
     * @작성자 : 이현도
     * @메소드설명 : 수신된 이메일을 DB에 저장하는 메소드
     */
    public void insertEmailList(List<ReceiveEmailDto> mailList) {
        for (ReceiveEmailDto receiveEmailDto : mailList) {
            try {
                // 중복 체크
                ReceiveEmailDto existingEmail = emailMapper
                        .selectEmailByTitleAndSenderName(receiveEmailDto.getTitle(), receiveEmailDto.getSenderName());
                if (existingEmail != null) {
                    // 이미 해당 제목과 동일한 수신자가 존재하면, 저장하지 않음
                    continue;
                }

                emailMapper.insertEmail(receiveEmailDto);

            } catch (Exception e) {
                log.error("이메일을 저장하는 중 예외가 발생했습니다.", e);

                throw new RuntimeException("이메일을 저장하는 중 예외가 발생했습니다.");
            }
        }
    }

    /**
     * @작성일 : 2023-03-24
     * @작성자 : 이현도
     * @메소드설명 : DB에 저장된 수신 이메일을 조회하는 메소드
     */
    public List<ReceiveEmailDto> selectEmailList() {
        try {
            return emailMapper.selectEmailList();

        } catch (Exception e) {
            log.error("이메일 목록을 조회하는 중 예외가 발생했습니다.", e);

            throw new RuntimeException("이메일 목록을 조회하는 중 예외가 발생했습니다.");
        }
    }

    /**
     * @작성일 : 2023-04-04
     * @작성자 : 이현도
     * @메소드설명 : DB에 저장된 발신 이메일 갯수를 조회하는 메소드
     */
    public int selectEmailListTotal() throws SQLException {
        int totalCount = 0;

        try {
            totalCount = emailMapper.selectEmailListTotal();

        } catch (Exception e) {
            throw new SQLException("발송 이메일을 조회하지 못했습니다.", e);
        }
        return totalCount;
    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : DB에서 페이지 처리가 된 발신 메일을 조회하는 메소드
     */
    public List<SendEmailDto> selectSendEmailListWithPaging(int startRow, int endRow) throws SQLException {

        List<SendEmailDto> sendList = Collections.emptyList();

        try {
            sendList = emailMapper.selectSendEmailListWithPaging(startRow, endRow);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendList;
    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 메일 번호를 이용하여 발신 이메일을 읽어오는 메소드
     */
    public SendEmailDto selectSendEmail(Long mailNo) {

        return emailMapper.selectSendEmail(mailNo);
    }

    public int selectReceiveEmailTotal() throws SQLException {
        int totalCount = 0;

        try {
            totalCount = emailMapper.selectReceiveEmailTotal();

            System.out.println("totalCount = " + totalCount);

        } catch (Exception e) {
            throw new SQLException("수신 이메일을 조회하지 못했습니다.", e);
        }
        return totalCount;
    }

    public List<ReceiveEmailDto> selectEmailListWithPaging(int startRow, int endRow) {
        List<ReceiveEmailDto> receiveList = Collections.emptyList();

        try {
            receiveList = emailMapper.selectEmailListWithPaging(startRow, endRow);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiveList;
    }
}
