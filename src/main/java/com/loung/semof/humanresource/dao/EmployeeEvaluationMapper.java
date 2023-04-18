package com.loung.semof.humanresource.dao;

import com.loung.semof.humanresource.dto.EmployeeEvaluationDto;
import org.apache.ibatis.annotations.Mapper;

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

    List<EmployeeEvaluationDto> selectAllContributionGrade();
}
