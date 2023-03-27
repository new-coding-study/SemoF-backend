//package com.loung.semof.email.service;
//
//import com.loung.semof.common.dao.EmployeeMapper;
//import com.loung.semof.common.dto.EmployeeDto;
//import com.loung.semof.email.dao.EmailMapper;
//import com.loung.semof.email.dto.EmailAttachDto;
//import com.loung.semof.email.dto.ReceiveEmailDto;
//import com.loung.semof.email.dto.SendEmailDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//import java.util.Optional;
//
///**
// * @파일이름 : EmailService.java
// * @프로젝트 : SemoF
// * @버전관리 : 1.0.0
// * @작성일 : 2023-03-23
// * @작성자 : 이현도
// * @클래스설명 : EmailController에서 요구하는 비즈니스 로직을 수행하는 클래스
// */
//@Slf4j
//@Service
//public class EmailService {
//
//    private final EmailMapper emailMapper;
//    private final JavaMailSender javaMailSender;
//    private final EmployeeMapper employeeMapper;
//
//    public EmailService(EmailMapper emailMapper, JavaMailSender javaMailSender, EmployeeMapper employeeMapper) {
//        this.emailMapper = emailMapper;
//        this.javaMailSender = javaMailSender;
//        this.employeeMapper = employeeMapper;
//    }
//
//    /**
//     * @작성일 : 2023-03-23
//     * @작성자 : 이현도
//     * @메소드설명 : empNo로 사원 정보를 조회해서 반환하는 메소드
//     */
//    public EmployeeDto getEmployee(Long empNo) {
//
//        Optional<EmployeeDto> employeeOptional = employeeMapper.selectByEmpNo(empNo);
//
//        if (employeeOptional.isPresent()) {
//            EmployeeDto employee = employeeOptional.get();
//
//            return EmployeeDto.builder()
//                    .empNo(employee.getEmpNo())
//                    .empName(employee.getEmpName())
//                    .empReg(employee.getEmpReg())
//                    .email(employee.getEmail())
//                    .phone(employee.getPhone())
//                    .address(employee.getAddress())
//                    .salary(employee.getSalary())
//                    .enrollDate(employee.getEnrollDate())
//                    .retireDate(employee.getRetireDate())
//                    .workStatus(employee.getWorkStatus())
//                    .gender(employee.getGender())
//                    .jobCode(employee.getJobCode())
//                    .deptCode(employee.getDeptCode())
//                    .branchCode(employee.getBranchCode())
//                    .build();
//        }
//        throw new RuntimeException("사원 정보를 찾을 수 없습니다.");
//    }
//
//    /**
//     * @작성일 : 2023-03-23
//     * @작성자 : 이현도
//     * @메소드설명 : EmailDto 객체에 저장된 정보를 사용하여 이메일을 발송하는 메소드
//     */
//    public void insertSendEmail(SendEmailDto emailDto) throws MessagingException {
//
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//
//        try {
//            helper.setSubject(emailDto.getTitle());
//
//            helper.setText(emailDto.getContent(), true);
//
//            helper.setFrom(emailDto.getSenderAddr(), emailDto.getSenderName());
//
//            helper.setTo(emailDto.getReceiverAddr());
//
//            List<EmailAttachDto> emailAttachDtoList = emailDto.getEmailAttachDtoList();
//
//            if (emailAttachDtoList != null && !emailAttachDtoList.isEmpty()) {
//                for (EmailAttachDto emailAttachDto : emailAttachDtoList) {
//                    File attachment = new File(emailAttachDto.getFilePath() + emailAttachDto.getChangeName());
//
//                    FileSystemResource file = new FileSystemResource(attachment);
//
//                    helper.addAttachment(emailAttachDto.getOriginName(), file);
//
//                    emailMapper.insertEmailAttach(emailAttachDto.getEmailFileNo(),
//                            emailAttachDto.getOriginName(),
//                            emailAttachDto.getChangeName(),
//                            emailAttachDto.getFilePath(),
//                            emailAttachDto.getUploadDate());
//                }
//            }
//
//            // 발송 전 Temp_Status 컬럼에 상태값을 업데이트
//            updateTempStatus(emailDto.getTempStatus());
//
//            javaMailSender.send(mimeMessage);
//
//            // 발송 후 Temp_Status 컬럼에 상태값을 업데이트
//            updateTempStatus("N");
//
//        } catch (MessagingException e) {
//            log.error("이메일을 보내는 중 예외가 발생했습니다.", e);
//
//            throw new RuntimeException("이메일을 보내는 중 예외가 발생했습니다.");
//
//        } catch (UnsupportedEncodingException e) {
//            log.error("인코딩 예외가 발생했습니다.", e);
//
//            throw new RuntimeException("인코딩 예외가 발생했습니다.");
//        }
//    }
//
//    /**
//     * @작성일 : 2023-03-24
//     * @작성자 : 이현도
//     * @메소드설명 : SendEmailDto 객체의 임시저장 상태값(tempStatus) 필드 값을 이메일 정보의 Temp_status 컬럼값으로 업데이트 하는 메소드
//     */
//    private void updateTempStatus(SendEmailDto emailDto) {
//        updateTempStatus(emailDto.getTempStatus(), emailDto.getMailNo());
//    }
//
//    /**
//     * @작성일 : 2023-03-24
//     * @작성자 : 이현도
//     * @메소드설명 : updateTempStatus를 오버로딩 하기 위한 메소드
//     */
//    private void updateTempStatus(String tempStatus) {}
//
//    /**
//     * @작성일 : 2023-03-24
//     * @작성자 : 이현도
//     * @메소드설명 : 오버로딩을 받아서 임시 저장 여부의 갱신 기능을 구현하는 메소드
//     */
//    private void updateTempStatus(String tempStatus, Long mailNo) {
//        Optional<SendEmailDto> optionalEmail = emailMapper.selectByEmailNo(mailNo);
//        if (optionalEmail.isPresent()) {
//            SendEmailDto email = optionalEmail.get();
//            email.setTempStatus(tempStatus != null ? tempStatus : "N");
//            emailMapper.insertSendEmail(email);
//        }
//    }
//
//    /**
//     * @작성일 : 2023-03-24
//     * @작성자 : 이현도
//     * @메소드설명 : 수신된 이메일을 DB에 저장하는 메소드
//     */
//    public void insertEmailList(List<ReceiveEmailDto> mailList) {
//        for (ReceiveEmailDto receiveEmailDto : mailList) {
//            try {
//                // 중복 체크
//                ReceiveEmailDto existingEmail = emailMapper
//                        .selectEmailByTitleAndSenderName(receiveEmailDto.getTitle(), receiveEmailDto.getSenderName());
//                if (existingEmail != null) {
//                    // 이미 해당 제목과 동일한 수신자가 존재하면, 저장하지 않음
//                    continue;
//                }
//
//                emailMapper.insertEmail(receiveEmailDto);
//
//            } catch (Exception e) {
//                log.error("이메일을 저장하는 중 예외가 발생했습니다.", e);
//
//                throw new RuntimeException("이메일을 저장하는 중 예외가 발생했습니다.");
//            }
//        }
//    }
//
//    /**
//     * @작성일 : 2023-03-24
//     * @작성자 : 이현도
//     * @메소드설명 : DB에 저장된 수신 이메일을 조회하는 메소드
//     */
//    public List<ReceiveEmailDto> selectEmailList() {
//        try {
//            return emailMapper.selectEmailList();
//
//        } catch (Exception e) {
//            log.error("이메일 목록을 조회하는 중 예외가 발생했습니다.", e);
//
//            throw new RuntimeException("이메일 목록을 조회하는 중 예외가 발생했습니다.");
//        }
//    }
//}
