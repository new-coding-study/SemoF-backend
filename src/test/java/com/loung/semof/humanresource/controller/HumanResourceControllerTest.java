//package com.loung.semof.humanresource.controller;
//
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.loung.semof.common.ResponseDto;
//import com.loung.semof.common.dto.EmployeeDto;
//import com.loung.semof.common.paging.ResponseDtoWithPaging;
//import com.loung.semof.common.paging.SelectCriteria;
//import com.loung.semof.humanresource.service.HumanResourceService;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * @파일이름 : HumanResourceControllerTest.java
// * @프로젝트 : SemoF
// * @버전관리 : 1.0.0
// * @작성일 : 2023-03-22
// * @작성자 : 이현도
// * @클래스설명 : HumanResource의 비즈니스 로직을 검증하기 위한 테스트 클래스
// */
////@ExtendWith(MockitoExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//class HumanResourceControllerTest {
//
//    @Mock
//    public HumanResourceService humanResourceService;
//
//    @InjectMocks
//    private HumanResourceController humanResourceController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private static final Logger log = LoggerFactory.getLogger(HumanResourceControllerTest.class);
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    private WebApplicationContext context;
//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .addFilter(new CharacterEncodingFilter("UTF-8", true))
//                .build();
//    }
//
//    @Rollback
//    @Test
//    void 사원_부서_발령_테스트() throws SQLException {
//
//        // given
//        EmployeeDto employeeDto = new EmployeeDto();
//
//        employeeDto.setEmpNo(77L);
//
//        employeeDto.setDeptCode("IT");
//
//        EmployeeDto employee = new EmployeeDto();
//
//        employee.setEmpNo(77L);
//
//        employee.setDeptCode("IT");
//
//        when(humanResourceService.updateEmployeeDepartment(anyLong(), anyString())).thenReturn(employee);
//
//        // when
//        ResponseEntity<ResponseDto> response = humanResourceController.updateEmployeeDepartment(employeeDto);
//
//        // then
//        assertThat(response).isNotNull();
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        assertThat(response.getBody().getMessage()).isEqualTo("발령 성공");
//
//        assertThat(response.getBody().getData()).isEqualTo(employee);
//
//        // add logging statement
//        log.info("Response: {}", response);
//    }
//
//
//    @Rollback
//    @Test
//    void 사원_지점_발령_테스트() throws SQLException {
//
//        // given
//        EmployeeDto employeeDto = EmployeeDto.builder()
//                .empNo(77L)
//                .deptCode("IT")
//                .branchCode(1L)
//                .build();
//
//        EmployeeDto updatedEmployee = EmployeeDto.builder()
//                .empNo(77L)
//                .deptCode("IT")
//                .branchCode(1L)
//                .build();
//
//        when(humanResourceService.updateEmployeeDepartment(anyLong(), anyString()))
//                .thenReturn(updatedEmployee);
//
//        // when
//        ResponseEntity<ResponseDto> response =
//                humanResourceController.updateEmployeeDepartment(employeeDto);
//
//        // then
//        assertThat(response).isNotNull();
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        ResponseDto responseDto = response.getBody();
//
//        assertThat(responseDto).isNotNull();
//
//        assertThat(responseDto.getMessage()).isEqualTo("발령 성공");
//
//        EmployeeDto actualEmployee = (EmployeeDto) responseDto.getData();
//
//        assertThat(actualEmployee).isEqualTo(updatedEmployee);
//
//        assertThat(actualEmployee.getBranchCode()).isEqualTo(1L);
//
//        // add logging statement
//        log.info("Response: {}", response);
//    }
//
//
//    // 사원 입력 테스트를 위한 상수화
//    private static final Long TEST_EMP_NO = 1L;
//    private static final Long TEST_JOB_CODE = 1L;
//    private static final Long TEST_BRANCH_CODE = 1L;
//    private static final String TEST_DEPT_CODE = "PL";
//    private static final String TEST_EMP_NAME = "홍길동";
//    private static final String TEST_EMP_REG = "000000-0000000";
//    private static final String TEST_EMAIL = "test@test.com";
//    private static final String TEST_PHONE = "010-1234-5678";
//    private static final String TEST_ADDRESS = "서울시 강남구";
//    private static final Integer TEST_SALARY = 5000;
//    private static LocalDateTime TEST_ENROLL_DATE;
//    private static final String TEST_WORK_STATUS = "재직";
//    private static final String TEST_GENDER = "남";
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
//    private LocalDateTime enrollDate;
//
//    @Rollback
//    @Test
//    void 사원_등록_테스트() throws SQLException {
//
//        // given
//
//        //builder로 작성하여 테스트로 입력 받을 정보의 템플릿을 작성
//        EmployeeDto employeeDto = EmployeeDto.builder()
//                .empNo(TEST_EMP_NO)
//                .empName(TEST_EMP_NAME)
//                .empReg(TEST_EMP_REG)
//                .email(TEST_EMAIL)
//                .phone(TEST_PHONE)
//                .address(TEST_ADDRESS)
//                .salary(TEST_SALARY)
//                .enrollDate(TEST_ENROLL_DATE)
//                .retireDate(null)
//                .workStatus(TEST_WORK_STATUS)
//                .gender(TEST_GENDER)
//                .jobCode(TEST_JOB_CODE)
//                .deptCode(TEST_DEPT_CODE)
//                .branchCode(TEST_BRANCH_CODE)
//                .build();
//
//        // 실제 사원 입력 테스트를 하는 코드
//        EmployeeDto insertedEmployee = EmployeeDto.builder()
//                .empNo(170L)
//                .empName("신유나")
//                .empReg("031209-4012560")
//                .email("itzy@test.com")
//                .phone("010-2019-5678")
//                .address("서울시 강남구")
//                .salary(5000000)
//                .enrollDate(LocalDateTime.now())
//                .retireDate(null)
//                .workStatus("재직")
//                .gender("여")
//                .jobCode(1L)
//                .deptCode("IT")
//                .branchCode(1L)
//                .build();
//
//        when(humanResourceService.insertEmployee(any(EmployeeDto.class)))
//                .thenReturn(insertedEmployee);
//
//        // when
//        ResponseEntity<ResponseDto> response =
//                humanResourceController.insertEmployee(employeeDto);
//
//        // then
//        assertThat(response).isNotNull();
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        ResponseDto responseDto = response.getBody();
//
//        assertThat(responseDto).isNotNull();
//
//        assertThat(responseDto.getMessage()).isEqualTo("사원등록 성공");
//
//        EmployeeDto actualEmployee = (EmployeeDto) responseDto.getData();
//
//        assertThat(actualEmployee).isEqualTo(insertedEmployee);
//
//        // add logging statement
//        log.info("Response: {}", response);
//    }
//
//    @Rollback
//    @Test
//    void 사원_수정_테스트() throws Exception {
//
//        //given
//        EmployeeDto employeeDto = new EmployeeDto();
//
//        employeeDto.setEmpNo(10L);
//        employeeDto.setPhone("010-1234-5678");
//        employeeDto.setEmail("atom@gmail.com");
//        employeeDto.setAddress("서울시 강남구");
//        employeeDto.setSalary(4000000);
//        employeeDto.setJobCode(5L);
//
//        //when
//        String responseContent = mockMvc.perform(MockMvcRequestBuilders.put("/employees/present")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(employeeDto)))
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
//
//        log.info("responseContent : " + responseContent);
//
//        // Then
//        JsonNode responseJson = objectMapper.readTree(responseContent);
//        assertEquals(200, responseJson.get("status").asInt());
//        assertEquals("수정 성공", responseJson.get("message").asText());
//
//        JsonNode data = responseJson.get("data");
//        assertEquals(employeeDto.getEmpNo(), data.get("empNo").asLong());
//        assertEquals(employeeDto.getPhone(), data.get("phone").asText());
//        assertEquals(employeeDto.getEmail(), data.get("email").asText());
//        assertEquals(employeeDto.getAddress(), data.get("address").asText());
//        assertEquals(employeeDto.getSalary(), data.get("salary").asInt());
//        assertEquals(employeeDto.getJobCode(), data.get("jobCode").asLong());
//    }
//
//    @Test
//    public void 사원_전체_조회_테스트() throws SQLException, UnsupportedEncodingException {
//        // Given
//        int pageNo = 1;
//        int totalCount = 30;
//        int limit = 10;
//        int buttonAmount = 5;
//        int maxPage = 3;
//        int startPage = 1;
//        int endPage = 3;
//        int startRow = 1;
//        int endRow = 10;
//
//        SelectCriteria selectCriteria = new SelectCriteria(pageNo, totalCount, limit, buttonAmount, maxPage, startPage, endPage, startRow, endRow);
//
//        List<EmployeeDto> employees = new ArrayList<>();
//
//        employees.add(new EmployeeDto(1L, "안유진", "030601-4234567", "yujinAn@gmail.com", "010-1234-5678", "서울특별시 강남구"
//                , 3000000, LocalDateTime.parse("2021-03-01 09:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), null, "Y", "F", 4L, "PL", 1L));
//        employees.add(new EmployeeDto(2L, "박지민", "000702-4345678", "parkjimin@gmail.com", "010-2345-6789", "서울특별시 강동구", 2800000
//                , LocalDateTime.parse("2021-03-01 09:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), null, "Y", "M", 5L, "HR", 1L));
//
//        log.info("[HumanResourceControllerTest] employees : " + employees);
//
//        when(humanResourceService.selectEmployeeTotal()).thenReturn(totalCount);
//        when(humanResourceService.selectEmployeeListWithPaging(anyInt(), anyInt())).thenReturn(employees);
//
//        // When
//        ResponseEntity<ResponseDto> response = humanResourceController.selectEmployeeListWithPaging(pageNo);
//
//        log.info("[HumanResourceControllerTest] response: " + response);
//
//        // Then
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody().getMessage()).isEqualTo("조회 성공");
//        assertThat(response.getBody().getData()).isInstanceOf(ResponseDtoWithPaging.class);
//
//        ResponseDtoWithPaging responseDtoWithPaging = (ResponseDtoWithPaging) response.getBody().getData();
//
//        log.info("[HumanResourceControllerTest] ResponseDtoWithPaging : " + responseDtoWithPaging);
//
//        assertThat(responseDtoWithPaging.getPageInfo()).isEqualTo(selectCriteria);
//        assertThat(responseDtoWithPaging.getData()).isEqualTo(employees);
//    }
//
//    @Rollback
//    @Test
//    public void 사원_조건_선택_테스트() throws Exception {
//        // given
//        EmployeeDto employeeDto = new EmployeeDto();
//        employeeDto.setEmpNo(1L);
//        employeeDto.setEmpName("김민지");
//        employeeDto.setEmail("kimminji@gmail.com");
//        employeeDto.setPhone("010-1234-5678");
//        employeeDto.setSalary(3000000);
//        employeeDto.setJobCode(4L);
//        employeeDto.setBranchCode(1L);
//        employeeDto.setDeptCode("PL");
//
//        List<EmployeeDto> employeeDtoList = new ArrayList<>();
//
//        employeeDtoList.add(employeeDto);
//
//        log.info("EmployeeDtoList: " + employeeDtoList);
//
//        // mock the service response
//        when(humanResourceService.selectEmployees(anyString(), anyString(), eq(0L)))
//                .thenReturn(employeeDtoList);
//
//        // when
//
////        MvcResult mvcResult = mockMvc.perform(get("/employees/present?deptCode=HR"))
////                .andExpect(status().isOk())
////                .andReturn();
//
////        MvcResult mvcResult = mockMvc.perform(get("/employees/present?empName=김민지"))
////                .andExpect(status().isOk())
////                .andReturn();
//
//        MvcResult mvcResult = mockMvc.perform(get("/employees/present?branchCode=1"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // then
//        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
//        ResponseDto responseDto = new ObjectMapper().readValue(content, ResponseDto.class);
//        assertEquals(HttpStatus.OK.value(), responseDto.getStatus());
//        assertEquals("조회 성공", responseDto.getMessage());
//        assertEquals(16, ((List)responseDto.getData()).size());
//    }
//
//
//}