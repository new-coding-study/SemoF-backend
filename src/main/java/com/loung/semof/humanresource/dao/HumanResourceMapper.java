package com.loung.semof.humanresource.dao;

import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.humanresource.dto.HumanResourceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    void updateEmployeeBranch(EmployeeDto employee);

    int selectEmployeeTotal();

    List<EmployeeDto> selectEmployeeListWithPaging(int startRow, int endRow);

    EmployeeDto selectEmployee(String empName, String deptCode, Long branchCode);

    List<EmployeeDto> selectEmployeeByBirthMonth(int monthValue);

    List<EmployeeDto> selectEmployeeByBirthMonthAfter(int monthValue);

    List<HumanResourceDto> selectByEmpName(@Param("empName") String empName);

    List<HumanResourceDto> selectByDeptName(@Param("deptName") String deptName);

    List<HumanResourceDto> selectByBranchName(@Param("branchName") String branchName);

}