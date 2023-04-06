package com.loung.semof.attendance.service;

import com.loung.semof.attendance.dao.AttendanceMapper;
import com.loung.semof.attendance.dto.AttendanceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;

    public AttendanceService(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    /**
     * @작성일 : 2023-03-27 027
     * @작성자 : sik
     * @메소드설명 : 사원 근태정보 상세 조회
     */
    public AttendanceDto selectAttendanceDetail(int empNo) throws Exception{
        log.info("[AttendanceService] selectAttendanceDetail Start ===================================");
        AttendanceDto attendanceDto = attendanceMapper.selectAttendanceDetail(empNo);
        LocalDate currentDate = LocalDate.now();
        if (attendanceDto.getAtdDate() == null || !Objects.equals(currentDate.toString(), attendanceDto.getAtdDate().substring(0, 10))){
            System.out.println("-------------날짜 비교 if문 진입-------------");
            attendanceDto.setStatusName(null);
        }
        log.info("[AttendanceService] selectAttendanceDetail End ===================================");
        if (attendanceDto == null) {
            throw new Exception("근태 정보 조회 실패");
        }
        return attendanceDto;
    }

    public List<AttendanceDto> selectAttendanceList(int empNo) throws Exception {
        log.info("[AttendanceService] selectAttendanceList Start ===================================");
        List<AttendanceDto> attendanceDtoList = attendanceMapper.selectAttendanceList(empNo);
        log.info("[AttendanceService] selectAttendanceList End ===================================");
        if (attendanceDtoList == null) {
            throw new Exception("근태 기록 조회 실패");
        }
        return attendanceDtoList;
    }

    @Transactional
    public String updateAttendance(HashMap<String, String> data) throws Exception{
        log.info("[AttendanceService] updateAttendance Start ===================================");

        // 사원번호만 꺼내서 조회용으로 사용
        int empNo = Integer.parseInt(data.get("empNo"));

        // 사원의 마지막 근무기록의 날짜 추출, 시스템 현재 날짜 조회
        AttendanceDto lastDate = attendanceMapper.selectAttendanceDetail(empNo);
        LocalDate currentDate = LocalDate.now();

        // 금일 근무기록이 없거나 마지막 근무일이 금일과 다를경우 현재일 기준의 행 추가 (날짜 같다면 당일 근무 기록이 이미 있음을 뜻함)
        if (lastDate.getAtdDate() == null || !Objects.equals(currentDate.toString(), lastDate.getAtdDate().substring(0, 10))){
            //lastDate == null 이였는데(행 자체가 null), 쿼리를 사원정보는 나오게 바꿔놔서 null비교문도 행 자체가 아닌 호출한 날짜로 비교)
            System.out.println("-------------날짜 비교 if문 진입-------------");
            attendanceMapper.insertAttendance(empNo);
        }

        // 현재일 기준으로 근무상태기록 행 카운트
        int countAtt = attendanceMapper.selectCountAttendanceStatusInfo(empNo);

        int statusCode;

        // 상태값과 카운트된 기록에 따라 처리 (0, 1 아니면 이미 퇴근한 상태니까 상태 변경할게 없음, 카운팅된 1이 퇴근일 케이스도 없음 0일때만 출근처리하고 그땐 출근으로 처리함)
        switch (countAtt) {
            case 0:
                log.info("-------------countAtt : 0(공석) -> 상태코드 : 1(출근)-------------");
                statusCode = 1;
                break;
            case 1:
                log.info("-------------countAtt : 1(출근) -> 상태코드 : 2(퇴근)-------------");
                statusCode = 2;
                break;
            default:
                log.info("-------------countAtt : 2(퇴근) -> 없음-------------");
                throw new Exception("에러 발생");
        }

        // 사원의 마지막 근무번호 조회(위에 생성된 기록 조회)
        int atdNo = attendanceMapper.selectLastAttendanceNo(empNo);

        // 근무상태정보 기록할 파라미터 값 저장
        data.put("atdNo", String.valueOf(atdNo));
        data.put("statusCode", String.valueOf(statusCode));

        // 파라미터 전달하여 근무상태기록
        attendanceMapper.insertAttendanceStatusInfo(data);

        // 파라미터 전달하여 근무 상태 변경
        int result = attendanceMapper.updateAttendance(atdNo, empNo, statusCode);
        log.info("[AttendanceService] updateAttendance End ===================================");
        log.info("[AttendanceService] result > 0 성공: "+ result);
        if (result > 0) {
            return "상태 변경 성공";
        } else {
            throw new Exception("상태 변경 실패");
        }
    }
}
