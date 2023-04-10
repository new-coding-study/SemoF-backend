package com.loung.semof.humanresource.dao;

import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.humanresource.dto.BranchOrderDto;
import com.loung.semof.humanresource.dto.DepartmentOrderDto;
import com.loung.semof.humanresource.dto.EmployeePhotoDto;
import com.loung.semof.humanresource.dto.HumanResourceDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @파일이름 : HumanResourceMapper.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : 비즈니스로직과 데이터베이스간의 상호작용을 수행하는 클래스
 */
@Mapper
public interface HumanResourceMapper {
    void updateEmployee(EmployeeDto employee);
    int selectEmployeeTotal();

    List<HumanResourceDto> selectEmployeeListWithPaging(int startRow, int endRow);

    List<HumanResourceDto> selectEmployees(String empName, String deptName, String branchName);

    List<EmployeeDto> selectEmployeeByBirthMonth(int monthValue);

    List<EmployeeDto> selectEmployeeByBirthMonthAfter(int monthValue);

    List<HumanResourceDto> SelectEmployeesForChart(String empName, String deptName, String branchName);

    List<HumanResourceDto> selectEmployeeByEmpNo(Long empNo);

    void insertEmployeePhoto(EmployeePhotoDto employeePhotoDto);

     EmployeePhotoDto selectEmployeePhotoByEmpNo(Long empNo);

    void deleteEmployeePhoto(Long photoNo);

    List<DepartmentOrderDto> selectDepartmentsOrders();

    List<BranchOrderDto> selectBranchesOrders();

    int selectBirthEmpCount(int monthValue);

    int selectTodayAttendanceList(LocalDate date);

    int selectVacationCount();

    Map<String, Long> selectGender();

    List<EmployeeDto> selectAllEmployees();
}