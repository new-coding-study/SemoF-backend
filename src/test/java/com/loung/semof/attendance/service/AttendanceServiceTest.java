package com.loung.semof.attendance.service;

import com.loung.semof.attendance.dto.AttendanceDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
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
    void 근태기록_갯수조회_성공() throws Exception {
        //given
        int empNo = 1;

        //when
        int totalCount = attendanceService.selectAttendanceTotal(empNo);

        //then
        System.out.println(totalCount);
        assertNotEquals(0, totalCount);
    }

    @Test
    void 근태기록_조회_성공() throws Exception {
        //given
        int empNo = 1;
        String offset = "1";

        //when
        int totalCount = attendanceService.selectAttendanceTotal(empNo);   //총 갯수 구하기
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(attendanceService.selectAttendanceListWithPaging(selectCriteria.getEndRow(), selectCriteria.getStartRow(), empNo));

        //then
        // responseDtoWithPaging.forEach(attendanceDto -> System.out.println("attendanceDto = " + attendanceDto));  //로그포제이 안 쓰고 그냥 출력문으로 확인
        System.out.println(responseDtoWithPaging);
        assertNotNull(responseDtoWithPaging);
    }

    @Test
    void 사원_근태_상태_변경_성공() throws Exception {
        //given
        int empNo = 1;
        HashMap<String, String> data = new HashMap<>();
        data.put("empNo", String.valueOf(empNo));

        //when
        String result = attendanceService.updateAttendance(data);

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

}