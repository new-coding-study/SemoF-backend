package com.loung.semof.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private int atdNo;  //근태번호
    private int empNo;  //사원번호
    private String empName; //사원이름
    private String statusName;  //상태명
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date atdTime;   //근무날짜
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime; //출근시간
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;   //퇴근시간
    private int allDays;    //총 연차
    private int usedDays;   //사용 연차
    private int leftDays;   //남은 연차
    // private MultipartFile AttendanceImage;
}
