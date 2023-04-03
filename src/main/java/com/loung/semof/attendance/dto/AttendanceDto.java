package com.loung.semof.attendance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private int atdNo;  //근태번호
    private int empNo;  //사원번호
    private String empName; //사원이름
    private String statusName;  //상태명
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    // @JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String atdDate;   //근무날짜
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // @JsonSerialize(using = CustomDateSerializer.class)
    private String startTime; //출근시간
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // @JsonSerialize(using = CustomDateSerializer.class)
    private String endTime;   //퇴근시간
    private int allDays;    //총 연차
    private int usedDays;   //사용 연차
    private int usedHalf;   //사용 반차
    private int leftDays;   //남은 연차
    // private MultipartFile AttendanceImage;
}
