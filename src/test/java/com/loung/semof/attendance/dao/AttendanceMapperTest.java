package com.loung.semof.attendance.dao;

import com.loung.semof.attendance.dto.AttendanceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @파일이름 : AttendanceMapperTest
 * @프로젝트 : SEMOF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-27 027
 * @작성자 : sik
 * @클래스설명 :
 */
@SpringBootTest
class AttendanceMapperTest {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Test
    public void testInit(){
        assertNotNull(attendanceMapper);
    }

    @Test
    void 근태정보_조회_성공() throws Exception {
        //given
        // FORNT에서 줄 값

        //when
        AttendanceDto attendanceDto = attendanceMapper.selectAttendanceDetail(1);
        LocalDate currentDate = LocalDate.now();
        if (attendanceDto.getAtdDate() == null || !Objects.equals(currentDate.toString(), attendanceDto.getAtdDate().substring(0, 10))){
            System.out.println("-------------날짜 비교 if문 진입-------------");
            attendanceDto.setStatusName(null);
        }

        //then
        System.out.println(attendanceDto);
        assertNotNull(attendanceDto);
    }

    // @Test
    // void 근태기록_조회_성공() throws Exception {
    //     //given
    //
    //     //when
    //     List<AttendanceDto> attendanceDtoList = attendanceMapper.selectAttendanceList(1);
    //
    //     //then
    //     attendanceDtoList.forEach(attendanceDto -> System.out.println("attendanceDto = " + attendanceDto));  //로그포제이 안 쓰고 그냥 출력문으로 확인
    //     // System.out.println(attendanceDtoList);
    //     assertNotNull(attendanceDtoList);
    // }

    @Test
    void 근태기록_갯수_조회_성공() throws Exception {
        //given
        int empNo = 1;

        //when
        int result = attendanceMapper.selectAttendanceTotal(empNo);

        //then
        // attendanceDtoList.forEach(attendanceDto -> System.out.println("attendanceDto = " + attendanceDto));  //로그포제이 안 쓰고 그냥 출력문으로 확인
        System.out.println(result);
        assertNotEquals(0, result);
    }

    @Test
    void 근태기록_조회_성공() throws Exception {
        //given
        // SelectCriteria selectCriteria = new SelectCriteria();
        // selectCriteria.setPageNo(1);
        // selectCriteria.setTotalCount(5);
        // selectCriteria.setLimit(10);
        // selectCriteria.setButtonAmount(5);
        // selectCriteria.setMaxPage(1);
        // selectCriteria.setStartPage(1);
        // selectCriteria.setEndPage(1);
        // selectCriteria.setStartRow(1);
        // selectCriteria.setEndRow(10);
        int endRow = 10;
        int startRow = 1;
        int empNo = 1;

        //when
        List<AttendanceDto> attendanceList = attendanceMapper.selectAttendanceListWithPaging(endRow, startRow, empNo);
        // List<AttendanceDto> attendanceDtoList = attendanceMapper.selectAttendanceList(1);

        //then
        attendanceList.forEach(attendanceDto -> System.out.println("attendanceDto = " + attendanceDto));  //로그포제이 안 쓰고 그냥 출력문으로 확인
        // System.out.println(attendanceDtoList);
        assertNotNull(attendanceList);
    }

    @Test
    void 사원_근태_상태_변경_성공() throws Exception {
        //given
        int empNo = 21;
        HashMap<String, String> data = new HashMap<>();
        data.put("empNo", String.valueOf(empNo));

        //when
        AttendanceDto lastDate = attendanceMapper.selectAttendanceDetail(empNo);
        LocalDate currentDate = LocalDate.now();
        // 금일 근무기록이 없거나 마지막 근무일이 금일과 다를경우 현재일 기준의 행 추가
        if (lastDate.getAtdDate() == null || !Objects.equals(currentDate.toString(), lastDate.getAtdDate().substring(0, 10))){
            //lastDate == null 이였는데(행 자체가 null), 쿼리를 사원정보는 나오게 바꿔놔서 null비교문도 행 자체가 아닌 호출한 날짜로 비교)
            System.out.println("-------------날짜 비교 if문 진입-------------");
            attendanceMapper.insertAttendance(empNo);
        }
        // 현재일 기준으로 근무상태기록 행 카운트
        int countAtt = attendanceMapper.selectCountAttendanceStatusInfo(empNo);
        int statusCode;
        // 상태값과 카운트된 기록에 따라 처리
        switch (countAtt) {
            case 0:
                System.out.println("-------------countAtt : 0(공석) -> 상태코드 : 1(출근)-------------");
                statusCode = 1;
                break;
            case 1:
                System.out.println("-------------countAtt : 1(출근) -> 상태코드 : 2(퇴근)-------------");
                statusCode = 2;
                break;
            default:
                System.out.println("-------------countAtt : 2(퇴근) -> 없음-------------");
                throw new Exception("에러 발생 (오늘 근무가 종료되었거나 잘못된 입력값");
        }
        int atdNo = attendanceMapper.selectLastAttendanceNo(empNo);
        data.put("atdNo", String.valueOf(atdNo));
        data.put("statusCode", String.valueOf(statusCode));
        attendanceMapper.insertAttendanceStatusInfo(data);
        int result = attendanceMapper.updateAttendance(atdNo, empNo, statusCode);

        //then
        System.out.println("data" + data);
        System.out.println("lastDate" + lastDate);
        System.out.println("currentDate" + currentDate);
        System.out.println("selectCountAttendanceStatusInfo : " + attendanceMapper.selectCountAttendanceStatusInfo(empNo));
        System.out.println("selectLastAttendanceNo : " + attendanceMapper.selectLastAttendanceNo(empNo));
        System.out.println(result > 0 ? "상태 변경 성공" : "상태 변경 실패");  //로그포제이 안 쓰고 그냥 출력문으로 확인
        assertEquals(1,result);
    }

    @Test
    void 만능_테스트_에오() throws Exception {
        //given
        int empNo = 2;

        //when
        AttendanceDto lastDate = attendanceMapper.selectAttendanceDetail(empNo);
        LocalDate currentDate = LocalDate.now();
        boolean tot = !Objects.equals(currentDate.toString(), lastDate.getAtdDate().substring(0, 10));

        //then
        System.out.println(lastDate.getAtdDate().substring(0, 10));  //로그포제이 안 쓰고 그냥 출력문으로 확인
        System.out.println(currentDate);
        assertTrue(tot);
    }
}