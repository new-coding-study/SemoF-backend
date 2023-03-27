package com.loung.semof.attendance.dao;

import com.loung.semof.attendance.dto.AttendanceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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