//package com.loung.semof.email.dao;
//
//import com.loung.semof.common.dto.EmployeeDto;
//import com.loung.semof.email.dto.ReceiveEmailDto;
//import com.loung.semof.email.dto.SendEmailDto;
//import org.apache.ibatis.annotations.Mapper;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
///**
// * @파일이름 : EmailMapper.java
// * @프로젝트 : SemoF
// * @버전관리 : 1.0.0
// * @작성일 : 2023-03-23
// * @작성자 : 이현도
// * @클래스설명 : 비즈니스로직과 데이터베이스간의 상호작용을 수행하는 클래스
// */
//@Mapper
//public interface EmailMapper {
//    void insertSendEmail(SendEmailDto email);
//    void insertEmail(ReceiveEmailDto receiveEmailDto);
//    List<ReceiveEmailDto> selectEmailList();
//
//    void insertEmailAttach(Long emailFileNo, String originName, String changeName, String filePath, LocalDateTime uploadDate);
//    EmployeeDto getEmployee(Long empNo);
//
//    ReceiveEmailDto selectEmailByTitleAndSenderName(String title, String senderName);
//}
