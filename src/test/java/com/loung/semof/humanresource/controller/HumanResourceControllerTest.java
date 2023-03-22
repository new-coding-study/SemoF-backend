package com.loung.semof.humanresource.controller;


import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.humanresource.service.HumanResourceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
    }
}