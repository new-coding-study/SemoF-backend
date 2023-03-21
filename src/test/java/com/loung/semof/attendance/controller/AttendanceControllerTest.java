package com.loung.semof.attendance.controller;

import com.loung.semof.attendance.dto.AttendanceDto;
import com.loung.semof.common.ResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
@SpringBootTest
// @ContextConfiguration(classes = {Chap01CrudApplication.class, MybatisConfig.class, ContextConfiguration.class})
class AttendanceControllerTest {

    @Autowired
    private AttendanceController attendanceController;

    /* //스프링에서 제공하는 가상 클래스
    private MockMvc mockMvc;

    @Test
    public void testInit(){
        assertNotNull(attendanceController);
        assertNotNull(mockMvc);
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceController).build();
    } */

    @Test
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

        // MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // params.add('');

        // when, then
        /* mockMvc.perform(MockMvcRequestBuilders.get("/status/1"))	//요청해오는 url
                .andExpect(MockMvcResultMatchers.status().isOk())				//넘기는거-메소드 체이닝 / 확인할 상태값
                // .andExpect(MockMvcResultMatchers.forwardedUrl("menu/list"))	//포워드해주는 url 확인
                .andDo(MockMvcResultHandlers.print());							//andDo : 추가로 실행할 메소드 작성 */

    }
}