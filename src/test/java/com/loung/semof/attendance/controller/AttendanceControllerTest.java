package com.loung.semof.attendance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loung.semof.attendance.dto.AttendanceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @파일이름 : AttendanceControllerTest
 * @프로젝트 : SEMOF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21 021
 * @작성자 : sik
 * @클래스설명 : 근태 테스트 클래스
 */

/**
 * @작성일 : 2023-03-21 021
 * @작성자 : sik
 * @메소드설명 : 근태 정보 조회 테스트 케이스
 */
// @SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AttendanceController.class)
class AttendanceControllerTest {

    @MockBean
    private AttendanceController attendanceController;

    //스프링에서 제공하는 가상 클래스
    @Autowired
    private MockMvc mockMvc;

    // @Test
    // public void testInit(){
    //     assertNotNull(attendanceController);
    //     assertNotNull(mockMvc);
    // }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceController).build();
    }
//낫널, 상태값, 메시지, 데이터, 로그

    @Test
    public void 단일_조회_테스트2() throws Exception {
        // given
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setAtdNo(1);
        attendanceDto.setEmpNo(1);
        attendanceDto.setEmpName("박지희");
        attendanceDto.setStatusName("출근");
        attendanceDto.setAtdTime(Date.valueOf("2023-03-21"));
        attendanceDto.setStartTime(Date.valueOf("2023-03-21"));
        attendanceDto.setEndTime(Date.valueOf("2023-03-21"));
        attendanceDto.setAllDays(15);
        attendanceDto.setUsedDays(2);
        attendanceDto.setLeftDays(13);

        String json = new ObjectMapper().writeValueAsString(attendanceDto);

        System.out.println(attendanceDto);
        System.out.println(json);

        mockMvc.perform(get("/attendance/status/1"))
                .andExpect(status().isOk())
                .andExpect(content())
                .andDo(log())
                .andDo(print());
    }

    /* @Test
    public void selectAttendanceDetail() throws Exception {
        // given
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setAtdNo(1);
        attendanceDto.setEmpNo(1);
        attendanceDto.setEmpName("박지희");
        attendanceDto.setStatusName("출근");
        attendanceDto.setAtdTime(Date.valueOf("2023-03-21"));
        attendanceDto.setStartTime(Date.valueOf("2023-03-21"));
        attendanceDto.setEndTime(Date.valueOf("2023-03-21"));
        attendanceDto.setAllDays(15);
        attendanceDto.setUsedDays(2);
        attendanceDto.setLeftDays(13);

        int empNo = attendanceDto.getEmpNo();

        //when
        ResponseEntity<ResponseDto> attendanceDto1 = attendanceController.selectAttendanceDetail(empNo);

        //then
        assertNotNull(attendanceDto1);
        assertEquals("사원 근태정보 조회 성공", "사원 근태정보 조회 성공", attendanceDto1.getBody().getMessage()); */



        /* assertEquals(예상, 실제) - 예상 값과 실제 값이 같은지 비교합니다.
        assertTrue(조건) - 주어진 조건이 참임을 확인합니다.
        assertFalse(조건) - 주어진 조건이 거짓임을 확인합니다.
        assertNull(object) - 주어진 객체가 null임을 확인합니다.
        assertNotNull(object) - 주어진 객체가 null이 아님을 확인합니다.
        assertSame(예상, 실제) - 예상 객체와 실제 객체가 동일한지(즉, 동일한 메모리 참조를 가짐) 비교합니다.
        assertNotSame(expected, actual) - 예상 객체와 실제 객체가 같지 않은지 비교합니다. */


        // MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // params.add('');

        // when, then
        /* mockMvc.perform(MockMvcRequestBuilders.get("/status/1"))	//요청해오는 url
                .andExpect(MockMvcResultMatchers.status().isOk())				//넘기는거-메소드 체이닝 / 확인할 상태값
                // .andExpect(MockMvcResultMatchers.forwardedUrl("menu/list"))	//포워드해주는 url 확인
                .andDo(MockMvcResultHandlers.print());							//andDo : 추가로 실행할 메소드 작성 */

    // }
}