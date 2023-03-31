package com.loung.semof.humanresource.dao;

import com.loung.semof.humanresource.dto.EmployeeEvaluationDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeEvaluationMapper {

    List<EmployeeEvaluationDto> selectAttendanceSummary(int year, int month, Long empNo);

    List<EmployeeEvaluationDto> selectProjectContributionRates(Long empNo);

    void insertAttendanceGrade(EmployeeEvaluationDto employeeEvaluationDto);

    void insertContributionGrade(EmployeeEvaluationDto employeeEvaluationDto);

    int countContributionEvaluation(EmployeeEvaluationDto employeeEvaluationDto);

    int countAttendanceEvaluation(EmployeeEvaluationDto employeeEvaluationDto);

    List<EmployeeEvaluationDto> selectAttendanceEvaluationByEmpNo(Long empNo);

    List<EmployeeEvaluationDto> selectContributionEvaluationByEmpNo(Long empNo);

    EmployeeEvaluationDto selectAttendanceEvaluationByEvalAtdNo(Long evalAtdNo);

    Long updateAttendanceGrade(EmployeeEvaluationDto employeeEvaluationDto);

    int countAttendanceEvaluationByEmpNo(Long empNo, Long categoryNo);

    Long updateContributionGrade(EmployeeEvaluationDto employeeEvaluationDto);

    List<EmployeeEvaluationDto> selectAttendanceByEmpNo(Long empNo);

    int deleteAttendanceGradeByEmpNo(Long targetNumber);

    EmployeeEvaluationDto selectContributionByEmpNo(Long empNo);

    int deleteContributionEvaluationByContNo(Long targetNumber);



}
