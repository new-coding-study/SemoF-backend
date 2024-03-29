package com.loung.semof.email.dao;

import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.email.dto.EmailAttachDto;
import com.loung.semof.email.dto.EmailDto;
import com.loung.semof.email.dto.ReceiveEmailDto;
import com.loung.semof.email.dto.SendEmailDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @파일이름 : EmailMapper.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-23
 * @작성자 : 이현도
 * @클래스설명 : 비즈니스로직과 데이터베이스간의 상호작용을 수행하는 클래스
 */
@Mapper
public interface EmailMapper {
    void insertSendEmail(SendEmailDto email);
    void insertEmail(ReceiveEmailDto receiveEmailDto);
    List<ReceiveEmailDto> selectEmailList();

    void insertEmailAttach(Long emailFileNo, String originName, String changeName, String filePath, LocalDateTime uploadDate);
    EmployeeDto getEmployee(Long empNo);

    ReceiveEmailDto selectEmailByTitleAndSenderName(String title, String senderName);

    Optional<SendEmailDto> selectByEmailNo(Long mailNo);

    List<SendEmailDto> selectByTempStatus(String y);

    void updateEmailStatus(Long mailNo);

    int selectEmailListTotal();

    List<SendEmailDto> selectSendEmailListWithPaging(int startRow, int endRow);

    SendEmailDto selectSendEmail(Long mailNo);

    int selectReceiveEmailTotal();

    List<ReceiveEmailDto> selectEmailListWithPaging(int startRow, int endRow);

    ReceiveEmailDto selectLastEmail();

    ReceiveEmailDto selectReceiveEmail(Long receiveNo);

    void insertEmailAttach(EmailAttachDto emailAttachDto);

    void updateSendTrash(Long mailNo);

    void updateReceiveTrash(Long mailNo);

    int selectTrashSendListTotal();

    int selectTrashReceiveListTotal();

    List<EmailDto> selectTrashEmailListWithPaging(int startRow, int endRow);

    int selectSendByTitleTotalCount();

    int selectReceiveByTitleTotalCount();

    List<EmailDto> searchEmailByTitle(String searchKeyword, int startRow, int endRow);
}
