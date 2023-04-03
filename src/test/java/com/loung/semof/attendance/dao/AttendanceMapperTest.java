package com.loung.semof.attendance.dao;

import com.loung.semof.attendance.dto.AttendanceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
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

        //when
        AttendanceDto attendanceDto = attendanceMapper.selectAttendanceDetail(1);

        //then
        System.out.println(attendanceDto);
        assertNotNull(attendanceDto);
    }

    @Test
    void 근태기록_조회_성공() throws Exception {
        //given

        //when
        List<AttendanceDto> attendanceDtoList = attendanceMapper.selectAttendanceList(1);

        //then
        attendanceDtoList.forEach(attendanceDto -> System.out.println("attendanceDto = " + attendanceDto));  //로그포제이 안 쓰고 그냥 출력문으로 확인
        // System.out.println(attendanceDtoList);
        assertNotNull(attendanceDtoList);
    }

    @Test
    void 연차_현황_조회_성공() throws Exception {
        //given

        //when
        AttendanceDto attendanceDto = attendanceMapper.selectVacationDetail(1);

        //then
        System.out.println(attendanceDto);  //로그포제이 안 쓰고 그냥 출력문으로 확인
        assertNotNull(attendanceDto);
    }

    @Test
    void 사원_근태_상태_변경_성공() throws Exception {
        //given
        int empNo = 1;

        //when
        AttendanceDto lastDate = attendanceMapper.selectAttendanceDetail(empNo);
        LocalDate currentDate = LocalDate.now();
        if (lastDate == null || !Objects.equals(currentDate.toString(), lastDate.getAtdDate().substring(0, 10))){
            System.out.println("-------------날짜 비교 if문 진입-------------");
            attendanceMapper.insertAttendance(empNo);
        }
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
        // HashMap<String, Integer> atdObject = new HashMap<>();
        // atdObject.put("atdNo", atdNo);
        // atdObject.put("statusCode", statusCode);
        // atdObject.put("empNo", empNo);
        attendanceMapper.insertAttendanceStatusInfo(atdNo, statusCode);
        int result = attendanceMapper.updateAttendance(atdNo, empNo, statusCode);

        //then
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
        int empNo = 1;

        //when
        AttendanceDto lastDate = attendanceMapper.selectAttendanceDetail(empNo);
        LocalDate currentDate = LocalDate.now();
        boolean tot = !Objects.equals(lastDate.toString(), currentDate.toString());

        //then
        System.out.println(lastDate.getAtdDate().substring(0, 10));  //로그포제이 안 쓰고 그냥 출력문으로 확인
        System.out.println(currentDate);
        assertTrue(tot);
    }



    /* @Test
    @DisplayName("신규 메뉴 추가 확인")
    public void 신규_메뉴_추가용_매퍼_테스트() {

        //given
        MenuDTO menu = new MenuDTO();
        menu.setName("JUnitTestMenu");
        menu.setPrice(10000);
        menu.setCategoryCode(1);
        menu.setOrderableStatus("Y");

        //when
        int result = menuMapper.registMenu(menu);

        //then
        assertEquals(1, result);
    } */

}