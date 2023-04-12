package com.loung.semof.email.service;

import com.loung.semof.common.dao.EmployeeMapper;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.email.config.EmailConfig;
import com.loung.semof.email.dao.EmailMapper;
import com.loung.semof.email.dto.EmailAttachDto;
import com.loung.semof.email.dto.EmailDto;
import com.loung.semof.email.dto.ReceiveEmailDto;
import com.loung.semof.email.dto.SendEmailDto;
import com.loung.semof.email.utils.ByteArrayResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import java.io.*;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
    private final EmailConfig emailConfig;

    private Set<String> processedMessageIds = new HashSet<>();
    public EmailService(EmailMapper emailMapper, JavaMailSender javaMailSender, EmployeeMapper employeeMapper, EmailConfig emailConfig) {
        this.emailMapper = emailMapper;
        this.javaMailSender = javaMailSender;
        this.employeeMapper = employeeMapper;
        this.emailConfig = emailConfig;
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
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : EmailDto 객체에 저장된 정보를 사용하여 이메일을 발송하는 메소드 수정
     */
    public void insertSendEmail(SendEmailDto emailDto, MultipartFile file) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setSubject(emailDto.getTitle());

        helper.setText(emailDto.getContent(), true);

        helper.setFrom(emailDto.getSenderAddr(), emailDto.getSenderName());

        helper.setTo(emailDto.getReceiverAddr());

        List<EmailAttachDto> emailAttachDtoList = emailDto.getEmailAttachDtoList();

        if (emailAttachDtoList != null && !emailAttachDtoList.isEmpty()) {

            for (EmailAttachDto emailAttachDto : emailAttachDtoList) {

                //DB에 첨부 파일 저장
                emailMapper.insertEmailAttach(emailAttachDto);

                // 사용자 지정 ByteArrayResource를 사용하여 이메일에 파일을 첨부
                ByteArrayResource byteArrayResource = new ByteArrayResource(emailAttachDto.getFileData());

                helper.addAttachment(emailAttachDto.getOriginName(), byteArrayResource);
            }
        }

        javaMailSender.send(mimeMessage);

        emailMapper.insertSendEmail(emailDto);
    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 파일을 저장하기 위한 메소드
     */
    private File saveUploadedFile(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            File tempFile = File.createTempFile("email_attach_", "_" + file.getOriginalFilename());

            try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(in, out);
            }
            return tempFile;
        }
        return null;
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
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 이메일 수신함과 관련된 기능을 수행하는 메소드
     */
    public void fetchEmailsFromGmailAndStore() {

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

            // Get the last email's send date from the database
            ReceiveEmailDto lastEmail = emailMapper.selectLastEmail();

            LocalDateTime lastEmailSendDate = lastEmail != null ? lastEmail.getSendDate() : null;

            // Fetch only new emails
            SearchTerm searchTerm = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    try {
                        // Excludes deleted messages
                        if (message.isSet(Flags.Flag.DELETED)) {
                            return false;
                        }
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }

                    // Match new emails based on lastEmailSendDate
                    if (lastEmailSendDate != null) {
                        try {
                            LocalDateTime messageSendDate = LocalDateTime.ofInstant(message.getSentDate().toInstant(), ZoneId.systemDefault());
                            return messageSendDate.isAfter(lastEmailSendDate);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            };

            Message[] messages = searchTerm != null ? inbox.search(searchTerm) : inbox.getMessages();

//            Message[] messages = inbox.getMessages();

            System.out.println("Fetched messages count: " + messages.length);

            for (Message message : messages) {
                ReceiveEmailDto receiveEmailDto = new ReceiveEmailDto();

                receiveEmailDto.setReceiverAddr(Arrays.toString(message.getRecipients(Message.RecipientType.TO)));

                receiveEmailDto.setSenderName(message.getFrom()[0].toString());

                receiveEmailDto.setTitle(message.getSubject());

                Object content = message.getContent();

                System.out.println("Content Type: " + message.getContentType());
                System.out.println("Raw Content: " + content);

                if (content instanceof MimeMultipart) {
                    MimeMultipart mimeMultipart = (MimeMultipart) content;

                    receiveEmailDto.setContent(getTextFromMimeMultipart(mimeMultipart));
                } else {
                    receiveEmailDto.setContent(content.toString());
                }

                receiveEmailDto.setSendDate(LocalDateTime.ofInstant(message.getSentDate().toInstant(), ZoneId.systemDefault()));

                mailList.add(receiveEmailDto);
            }

            inbox.close(false);

            store.close();

            insertEmailList(mailList);

            List<ReceiveEmailDto> emailList = selectEmailList();

            System.out.println("Email list from database: " + emailList);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 수신 이메일 변환 수행하는 메소드
     */
    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        String htmlContent = null;

        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);

            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                htmlContent = (String) bodyPart.getContent();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }

        if (result.length() == 0 && htmlContent != null) {
            result.append("\n").append(Jsoup.parse(htmlContent).text());
        }

        return result.toString();
    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 이메일을 수신 성능을 위해 최신 이메일을 가져오는 기능을 수행하는 메소드
     */
    public LocalDateTime getLastFetchedTimestamp() {
        try {
            ReceiveEmailDto lastEmail = emailMapper.selectLastEmail();

            return lastEmail == null ? null : lastEmail.getSendDate();

        } catch (Exception e) {
            log.error("마지막으로 가져온 이메일 타임스탬프를 가져오는 동안 오류가 발생했습니다.", e);

            throw new RuntimeException("마지막으로 가져온 이메일 타임스탬프를 가져오는 동안 오류가 발생했습니다.");
        }
    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 조회 기간을 설정하는 메소드
     */
    public SearchTerm searchForNewEmails(LocalDateTime lastEmailSendDate) {
        Instant lastEmailInstant = lastEmailSendDate.atZone(ZoneId.systemDefault()).toInstant();

        Date lastEmailDate = Date.from(lastEmailInstant);

        // Create a search term for all emails received after the lastEmailDate
//        SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT, lastEmailDate);
        SearchTerm newerThan = new FlagTerm(new Flags(Flags.Flag.RECENT), true);

        return newerThan;
    }

    /**
     * @작성일 : 2023-03-24
     * @작성자 : 이현도
     * @메소드설명 : 수신된 이메일을 DB에 저장하는 메소드
     */
    public void insertEmailList(List<ReceiveEmailDto> mailList) {

        System.out.println("mailList size = " + mailList.size());

        int processedEmails = 0;

        int insertedEmails = 0;

        int skippedEmails = 0;

        for (ReceiveEmailDto receiveEmailDto : mailList) {
            try {
                // 중복 체크
                ReceiveEmailDto existingEmail = emailMapper
                        .selectEmailByTitleAndSenderName(receiveEmailDto.getTitle(), receiveEmailDto.getSenderName());
                if (existingEmail != null) {
                    // 이미 해당 제목과 동일한 수신자가 존재하면, 저장하지 않음
                    skippedEmails++;
                    continue;
                }

                emailMapper.insertEmail(receiveEmailDto);
                insertedEmails++;

            } catch (Exception e) {
                log.error("이메일을 저장하는 중 예외가 발생했습니다.", e);
                throw new RuntimeException("이메일을 저장하는 중 예외가 발생했습니다.");
            }

            processedEmails++;
        }
        log.info("Processed emails count: " + processedEmails);

        log.info("Inserted emails count: " + insertedEmails);

        log.info("Skipped emails count: " + skippedEmails);
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

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 수신함 전체 갯수를 카운트하는 메소드
     */
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

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 수신 메일을 조회하여 페이지네이션 처리를 하는 메소드
     */
    public List<ReceiveEmailDto> selectEmailListWithPaging(int startRow, int endRow) {
        List<ReceiveEmailDto> receiveList = Collections.emptyList();

        try {
            receiveList = emailMapper.selectEmailListWithPaging(startRow, endRow);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiveList;
    }

    /**
     * @작성일 : 2023-04-05
     * @작성자 : 이현도
     * @메소드설명 : 메일 번호에 따라 수신 메일을 조회하는 메소드
     */
    public ReceiveEmailDto selectReceiveEmail(Long receiveNo) {
        return emailMapper.selectReceiveEmail(receiveNo);
    }

    /**
     * @작성일 : 2023-04-06
     * @작성자 : 이현도
     * @메소드설명 : 메일함 카테고리와, 메일 번호를 활용하여 메일의 상태값을 변경하는 메소드
     */
    public String updateToTrash(Long mailNo, String category) {
        if ("send".equals(category)) {
            emailMapper.updateSendTrash(mailNo);

        } else if ("receive".equals(category)) {
            emailMapper.updateReceiveTrash(mailNo);

        } else {
            throw new IllegalArgumentException("부정확한 카테고리 입니다.");
        }
        return "삭제 성공";
    }

    /**
     * @작성일 : 2023-04-08
     * @작성자 : 이현도
     * @메소드설명 : 삭제로 변경된 발신 메일 숫자를 조회하는 비즈니스 로직
     */
    public int selectTrashSendListTotal() throws SQLException {
        int totalCount = 0;

        try {
            totalCount = emailMapper.selectTrashSendListTotal();

        } catch (Exception e) {
            throw new SQLException("삭제 된 이메일을 조회하지 못했습니다.", e);
        }
        return totalCount;
    }

    /**
     * @작성일 : 2023-04-08
     * @작성자 : 이현도
     * @메소드설명 : 삭제로 변경된 수신 메일 숫자를 조회하는 비즈니스 로직
     */
    public int selectTrashReceiveListTotal() throws SQLException {
        int totalCount = 0;

        try {
            totalCount = emailMapper.selectTrashReceiveListTotal();

        } catch (Exception e) {
            throw new SQLException("삭제 된 이메일을 조회하지 못했습니다.", e);
        }
        return totalCount;
    }

    /**
     * @작성일 : 2023-04-09
     * @작성자 : 이현도
     * @메소드설명 : 휴지통에 있는 메일을 조회하는 비즈니스 로직
     */
    public List<EmailDto> selectTrashEmailListWithPaging(int startRow, int endRow) {

        List<EmailDto> trashList = Collections.emptyList();

        try {
            trashList = emailMapper. selectTrashEmailListWithPaging(startRow, endRow);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return trashList;
    }

    /**
     * @작성일 : 2023-04-09
     * @작성자 : 이현도
     * @메소드설명 : 발신함에서 키워드와 일치하는 숫자를 카운트하는 비즈니스 로직
     */
    public int selectSendByTitleTotalCount(String searchKeyword) throws SQLException {
        int totalCount = 0;

        try {
            totalCount = emailMapper.selectSendByTitleTotalCount();

        } catch (Exception e) {
            throw new SQLException("삭제 된 이메일을 조회하지 못했습니다.", e);
        }
        return totalCount;
    }

    /**
     * @작성일 : 2023-04-09
     * @작성자 : 이현도
     * @메소드설명 : 수신함에서 키워드와 일치하는 숫자를 카운트하는 비즈니스 로직
     */
    public int selectReceiveByTitleTotalCount(String searchKeyword) throws SQLException {
        int totalCount = 0;

        try {
            totalCount = emailMapper.selectReceiveByTitleTotalCount();

        } catch (Exception e) {
            throw new SQLException("삭제 된 이메일을 조회하지 못했습니다.", e);
        }
        return totalCount;
    }

    public List<EmailDto> searchEmailByTitle(String searchKeyword, int startRow, int endRow) {

        List<EmailDto> searchList = Collections.emptyList();

        try {
            searchList = emailMapper. searchEmailByTitle(searchKeyword, startRow, endRow);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return searchList;

    }
}
