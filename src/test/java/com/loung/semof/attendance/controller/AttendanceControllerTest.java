package com.loung.semof.attendance.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @파일이름 : AttendanceControllerTest
 * @프로젝트 : SEMOF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21 021
 * @작성자 : sik
 * @클래스설명 : 근태 컨트롤러 테스트 클래스
 */

/**
 * @작성일 : 2023-03-21 021
 * @작성자 : sik
 * @메소드설명 : 근태 정보 조회 테스트 케이스
 */

@SpringBootTest
// @RunWith(SpringRunner.class)
// @WebMvcTest(controllers = AttendanceController.class)
class AttendanceControllerTest {


    @Autowired
    private AttendanceController attendanceController;

    // 스프링에서 제공하는 가상 클래스
    // @MockBean
    private MockMvc mockMvc;

    @Test
    public void testInit() {
        assertNotNull(attendanceController);
        assertNotNull(mockMvc);
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(attendanceController).build();
    }

    @Test
    @DisplayName("selectAttendanceDetail_success")
    public void 사원_근태_정보_조회_성공() throws Exception {
        // given
        // when, then
        mockMvc.perform(get("/attendance/status/{empNo}", 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }
    @Test
    public void 사원_근태_정보_조회_실패() throws Exception {
        // given
        // when, then
        mockMvc.perform(get("/attendance/status/{empNo}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect((ResultMatcher) assertThrows(Exception.class, () -> attendanceController.selectAttendanceDetail(1), "실패"))
                .andDo(print());
    }

    @Test
    @DisplayName("selectAttendanceList_success")
    public void 사원_근태_기록_조회_성공() throws Exception {
        // given
        // when, then
        mockMvc.perform(get("/attendance/status/histories/{empNo}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    @DisplayName("selectVacationDetail_success")
    public void 사원_연차_현황_조회_성공() throws Exception {
        // given
        // when, then
        mockMvc.perform(get("/attendance/annual/{empNo}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    @DisplayName("updateAttendance_success")
    public void 사원_근태_상태_변경_성공() throws Exception {
        // given
        int empNo = 3;

        // when, then
        mockMvc.perform(put("/attendance/status/{empNo}", empNo))
                .andExpect(status().isOk())
                .andDo(print());
    }


    // MultiValueMap<String, String> params = new LinkedMultiValueMap<>();// 폼에서 넘어올 값을 미리 작성
    //         params.add("empNo", "1");
    //         params.add("statusCode", "0");

    // mockMvc.perform(put("/attendance/status/{empNo}", empNo)) uriVariables 바로 대입가능 (ex: 1)

    // DB랑 연결해서 TDD 실행하여 조회하는게 맞음, R은 실제 DB에 접근해서 조회고 CUD는 실제 DB에 반영되면 안 되므로 트랜젝션 처리를 가상으로 해주기 위해 가상의 값을 넣어서 테스트 함

    // .param() | .accept(MediaType.APPLICATION_JSON)





    // AttendanceDto attendanceDto = new AttendanceDto();
    //         attendanceDto.setAtdNo(1);
    //         attendanceDto.setEmpNo(1);
    //         attendanceDto.setEmpName("박지희");
    //         attendanceDto.setStatusName("출근");
    //         attendanceDto.setAtdTime(Date.valueOf("2023-03-21"));
    //         attendanceDto.setStartTime(Date.valueOf("2023-03-21"));
    //         attendanceDto.setEndTime(Date.valueOf("2023-03-21"));
    //         attendanceDto.setAllDays(15);
    //         attendanceDto.setUsedDays(2);
    //         attendanceDto.setLeftDays(13);
    //
    //         String json = new ObjectMapper().writeValueAsString(attendanceDto);
    //
    //         System.out.println(attendanceDto);
    //         System.out.println(json);

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

    // MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    // params.add('');

    // when, then
        /* mockMvc.perform(MockMvcRequestBuilders.get("/status/1"))	//요청해오는 url
                .andExpect(MockMvcResultMatchers.status().isOk())				//넘기는거-메소드 체이닝 / 확인할 상태값
                // .andExpect(MockMvcResultMatchers.forwardedUrl("menu/list"))	//포워드해주는 url 확인
                .andDo(MockMvcResultHandlers.print());							//andDo : 추가로 실행할 메소드 작성 */

    // }
}