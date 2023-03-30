package com.loung.semof.humanresource.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.humanresource.service.HumanResourceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @파일이름 : HumanResourceControllerTest.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-22
 * @작성자 : 이현도
 * @클래스설명 : HumanResource의 비즈니스 로직을 검증하기 위한 테스트 클래스
 */
@ExtendWith(MockitoExtension.class)
class HumanResourceControllerTest {

    @Mock
    private HumanResourceService humanResourceService;

    @InjectMocks
    private HumanResourceController humanResourceController;

    private static final Logger log = LoggerFactory.getLogger(HumanResourceControllerTest.class);

    @Test
    void 사원_부서_발령_테스트() throws SQLException {

        // given
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setEmpNo(77L);

        employeeDto.setDeptCode("IT");

        EmployeeDto employee = new EmployeeDto();

        employee.setEmpNo(77L);

        employee.setDeptCode("IT");

        when(humanResourceService.updateEmployeeDepartment(anyLong(), anyString())).thenReturn(employee);

        // when
        ResponseEntity<ResponseDto> response = humanResourceController.updateEmployeeDepartment(employeeDto);

        // then
        assertThat(response).isNotNull();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody().getMessage()).isEqualTo("발령 성공");

        assertThat(response.getBody().getData()).isEqualTo(employee);

        // add logging statement
        log.info("Response: {}", response);
    }


    @Test
    void 사원_지점_발령_테스트() throws SQLException {

        // given
        EmployeeDto employeeDto = EmployeeDto.builder()
                .empNo(77L)
                .deptCode("IT")
                .branchCode(1L)
                .build();

        EmployeeDto updatedEmployee = EmployeeDto.builder()
                .empNo(77L)
                .deptCode("IT")
                .branchCode(1L)
                .build();

        when(humanResourceService.updateEmployeeDepartment(anyLong(), anyString()))
                .thenReturn(updatedEmployee);

        // when
        ResponseEntity<ResponseDto> response =
                humanResourceController.updateEmployeeDepartment(employeeDto);

        // then
        assertThat(response).isNotNull();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseDto responseDto = response.getBody();

        assertThat(responseDto).isNotNull();

        assertThat(responseDto.getMessage()).isEqualTo("발령 성공");

        EmployeeDto actualEmployee = (EmployeeDto) responseDto.getData();

        assertThat(actualEmployee).isEqualTo(updatedEmployee);

        assertThat(actualEmployee.getBranchCode()).isEqualTo(1L);

        // add logging statement
        log.info("Response: {}", response);
    }


    // 사원 입력 테스트를 위한 상수화
    private static final Long TEST_EMP_NO = 1L;
    private static final Long TEST_JOB_CODE = 1L;
    private static final Long TEST_BRANCH_CODE = 1L;
    private static final String TEST_DEPT_CODE = "PL";
    private static final String TEST_EMP_NAME = "홍길동";
    private static final String TEST_EMP_REG = "000000-0000000";
    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_PHONE = "010-1234-5678";
    private static final String TEST_ADDRESS = "서울시 강남구";
    private static final Integer TEST_SALARY = 5000;
    private static LocalDateTime TEST_ENROLL_DATE;
    private static final String TEST_WORK_STATUS = "재직";
    private static final String TEST_GENDER = "남";
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime enrollDate;

    @Test
    void 사원_등록_테스트() throws SQLException {

        // given

        //builder로 작성하여 테스트로 입력 받을 정보의 템플릿을 작성
        EmployeeDto employeeDto = EmployeeDto.builder()
                .empNo(TEST_EMP_NO)
                .empName(TEST_EMP_NAME)
                .empReg(TEST_EMP_REG)
                .email(TEST_EMAIL)
                .phone(TEST_PHONE)
                .address(TEST_ADDRESS)
                .salary(TEST_SALARY)
                .enrollDate(TEST_ENROLL_DATE)
                .retireDate(null)
                .workStatus(TEST_WORK_STATUS)
                .gender(TEST_GENDER)
                .jobCode(TEST_JOB_CODE)
                .deptCode(TEST_DEPT_CODE)
                .branchCode(TEST_BRANCH_CODE)
                .build();

        // 실제 사원 입력 테스트를 하는 코드
        EmployeeDto insertedEmployee = EmployeeDto.builder()
                .empNo(170L)
                .empName("신유나")
                .empReg("031209-4012560")
                .email("itzy@test.com")
                .phone("010-2019-5678")
                .address("서울시 강남구")
                .salary(5000000)
                .enrollDate(LocalDateTime.now())
                .retireDate(null)
                .workStatus("재직")
                .gender("여")
                .jobCode(1L)
                .deptCode("IT")
                .branchCode(1L)
                .build();

        when(humanResourceService.insertEmployee(any(EmployeeDto.class)))
                .thenReturn(insertedEmployee);

        // when
        ResponseEntity<ResponseDto> response =
                humanResourceController.insertEmployee(employeeDto);

        // then
        assertThat(response).isNotNull();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseDto responseDto = response.getBody();

        assertThat(responseDto).isNotNull();

        assertThat(responseDto.getMessage()).isEqualTo("사원등록 성공");

        EmployeeDto actualEmployee = (EmployeeDto) responseDto.getData();

        assertThat(actualEmployee).isEqualTo(insertedEmployee);

        // add logging statement
        log.info("Response: {}", response);
    }

}