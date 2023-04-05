package com.loung.semof.attendance.service;

import com.loung.semof.attendance.dto.AttendanceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @파일이름 : AttendanceServiceTest
 * @프로젝트 : SEMOF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-27 027
 * @작성자 : sik
 * @클래스설명 : 근태 서비스 테스트 클래스
 */
@SpringBootTest
class AttendanceServiceTest {

    @Autowired
    private AttendanceService attendanceService;

    @Test
    public void testInit(){
        assertNotNull(attendanceService);
    }

    @Test
    void 근태정보_조회_성공() throws Exception {
        //given
        int empNo = 3;

        //when
        AttendanceDto attendanceDto = attendanceService.selectAttendanceDetail(empNo);
        LocalDate currentDate = LocalDate.now();
        if (attendanceDto.getAtdDate() == null || !Objects.equals(currentDate.toString(), attendanceDto.getAtdDate().substring(0, 10))){
            System.out.println("-------------날짜 비교 if문 진입-------------");
            attendanceDto.setStatusName(null);
        }

        //then
        System.out.println(attendanceDto);  //로그포제이 안 쓰고 그냥 출력문으로 확인
        assertNotNull(attendanceDto);
    }

    @Test
    void 근태기록_조회_성공() throws Exception {
        //given
        int empNo = 1;

        //when
        List<AttendanceDto> attendanceDtoList = attendanceService.selectAttendanceList(empNo);

        //then
        attendanceDtoList.forEach(attendanceDto -> System.out.println("attendanceDto = " + attendanceDto));  //로그포제이 안 쓰고 그냥 출력문으로 확인
        // System.out.println(attendanceDtoList);
        assertNotNull(attendanceDtoList);
    }

    @Test
    void 연차_현황_조회_성공() throws Exception {
        //given
        int empNo = 1;

        //when
        AttendanceDto attendanceDto = attendanceService.selectVacationDetail(empNo);

        //then
        System.out.println(attendanceDto);  //로그포제이 안 쓰고 그냥 출력문으로 확인
        assertNotNull(attendanceDto);
    }

    @Test
    void 사원_근태_상태_변경_성공() throws Exception {
        //given
        int empNo = 1;

        //when
        String result = attendanceService.updateAttendance(empNo);

        //then
        System.out.println("상태 변경 성공");  //로그포제이 안 쓰고 그냥 출력문으로 확인
        assertEquals("상태 변경 성공",result);
    }



/*      assertEquals(예상, 실제) - 예상 값과 실제 값이 같은지 비교합니다.
        assertTrue(조건) - 주어진 조건이 참임을 확인합니다.
        assertFalse(조건) - 주어진 조건이 거짓임을 확인합니다.
        assertNull(object) - 주어진 객체가 null임을 확인합니다.
        assertNotNull(object) - 주어진 객체가 null이 아님을 확인합니다.
        assertSame(예상, 실제) - 예상 객체와 실제 객체가 동일한지(즉, 동일한 메모리 참조를 가짐) 비교합니다.
        assertNotSame(expected, actual) - 예상 객체와 실제 객체가 같지 않은지 비교합니다. */

  /*   @Test
    public void 신규_메뉴_등록용_서비스_성공_테스트() throws Exception {

        //given
        MenuDTO menu = new MenuDTO();
        menu.setName("민트초코콩국수");
        menu.setPrice(30000);
        menu.setCategoryCode(5);
        menu.setOrderableStatus("Y");

        boolean result = menuService.registMenu(menu);

        assertTrue(result);
    }
    @Test
    public void 신규_메뉴_등록용_서비스_실패_테스트() throws Exception {

        //given
        MenuDTO menu = new MenuDTO();
        menu.setName("민트초코콩국수");
        menu.setPrice(30000);
        menu.setCategoryCode(5);
        menu.setOrderableStatus("Y");

        //boolean result = menuService.registMenu(menu);

        assertThrows(Exception.class, () -> menuService.registMenu(menu), "메뉴 등록 실패!");
        // assertThrows : Exception 이발생하면 테스트가 통과 ( 발생할 익셉션타입, 실행할 내용을  람다식으로 작성, 던지는 메세지 )

    } */
}