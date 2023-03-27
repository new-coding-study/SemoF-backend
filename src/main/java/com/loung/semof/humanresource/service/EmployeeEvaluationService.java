package com.loung.semof.humanresource.service;

import com.loung.semof.common.dao.EmployeeMapper;
import com.loung.semof.humanresource.Exception.NotFoundException;
import com.loung.semof.humanresource.dao.EmployeeEvaluationMapper;
import com.loung.semof.humanresource.dto.EmployeeEvaluationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmployeeEvaluationService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeEvaluationMapper employeeEvaluationMapper;

    public EmployeeEvaluationService(EmployeeMapper employeeMapper, EmployeeEvaluationMapper employeeEvaluationMapper) {
        this.employeeMapper = employeeMapper;
        this.employeeEvaluationMapper = employeeEvaluationMapper;
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 출근, 결근율 계산 비즈니스 로직을 수행하는 메소드
     */
    public Map<String, Object> selectAttendanceSummary(int empNo) {

        Map<String, Object> summary = new HashMap<>();

        int year = LocalDate.now().getYear();

        int month = LocalDate.now().getMonthValue();

        summary.put("year", year);

        summary.put("month", month);

        List<EmployeeEvaluationDto> list = employeeEvaluationMapper.selectAttendanceSummary(year, month, empNo);

        summary.put("attendance", list);

        double totalDays = YearMonth.of(year, month).lengthOfMonth();

        double totalWorkingDays = list.stream().mapToDouble(EmployeeEvaluationDto::getWorkingDays).sum();

        double absenceDays = totalDays - totalWorkingDays;

        double attendanceRate = totalWorkingDays / totalDays * 100;

        double absenceRate = absenceDays / totalDays * 100;

        summary.put("attendanceRate", attendanceRate);

        summary.put("absenceRate", absenceRate);

        return summary;
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 프로젝트 별 기여도 계산의 비즈니스 로직을 수행하는 메소드
     */
    public List<EmployeeEvaluationDto> selectProjectContributionRates(Long empNo) {

        List<EmployeeEvaluationDto> employeeEvaluationDtoList = employeeEvaluationMapper.selectProjectContributionRates(empNo);

        // 프로젝트 기여도 총합 계산
        Map<String, Long> projectContributionMap = new HashMap<>(); // 프로젝트 번호와 해당 프로젝트의 총 기여도를 저장할 Map 생성

        for (EmployeeEvaluationDto dto : employeeEvaluationDtoList) {
            // 프로젝트 이름과 해당 프로젝트의 기여도를 가져옴
            Map.Entry<String, Long>[] projectContributions = new Map.Entry[] {
                    Map.entry("A", dto.getProjAContribution()),
                    Map.entry("B", dto.getProjBContribution()),
                    Map.entry("C", dto.getProjCContribution())
            };

            for (Map.Entry<String, Long> projectContribution : projectContributions) {
                String projNo = projectContribution.getKey();

                Long contribution = projectContribution.getValue();

                if (contribution != null) {
                    if (projectContributionMap.containsKey(projNo)) {
                        projectContributionMap.put(projNo, projectContributionMap.get(projNo) + contribution);

                    } else {
                        projectContributionMap.put(projNo, contribution);
                    }
                }
            }
            // 각각의 Dto에 프로젝트 기여도 총합 추가
            Long projTotalContribution = dto.getProjAContribution() + dto.getProjBContribution() + dto.getProjCContribution();

            log.info("[EmployeeEvaluationService] projTotalContribution : " + projTotalContribution);

            dto.setProjTotalContribution(projTotalContribution);

            // 각각의 프로젝트의 기여도 총합을 더한 뒤 프로젝트 수로 나눠서 평균 계산
            double projAverageContribution = projTotalContribution / 3;

            dto.setProjAverageContribution(projAverageContribution);
        }
        return employeeEvaluationDtoList;
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 근태 평가 등록에 대한 비즈니스 로직을 실행하는 메서드
     */
    public Long  insertAttendanceGrade(EmployeeEvaluationDto employeeEvaluationDto) {

        // 근태 평가 정보가 이미 존재하는지 확인
        int count = employeeEvaluationMapper.countAttendanceEvaluation(employeeEvaluationDto);

        log.info("[EmployeeEvaluationService] count : " + count);

        if (count > 0) {
            throw new RuntimeException("이미 등록된 근태 평가 정보입니다.");
        }

        employeeEvaluationMapper.insertAttendanceGrade(employeeEvaluationDto);

        return employeeEvaluationDto.getEvalAtdNo();
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 기여도 평가 등록에 대한 비즈니스 로직을 실행하는 메서드
     */
    @Transactional
    public Long insertContributionGrade(EmployeeEvaluationDto employeeEvaluationDto) {

            // 기여도 평가 정보가 이미 존재하는지 확인
            int count = employeeEvaluationMapper.countContributionEvaluation(employeeEvaluationDto);

            log.info("[EmployeeEvaluationService] count : " + count);

            if (count > 0) {
                throw new RuntimeException("이미 등록된 기여도 평가 정보입니다.");
            }

            employeeEvaluationMapper.insertContributionGrade(employeeEvaluationDto);

            return employeeEvaluationDto.getEvalContNo();
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 근태 평가 점수 조회 비즈니스 로직을 수행하는 메소드
     */
    public List<EmployeeEvaluationDto> selectAttendanceGrade(int empNo) {

        List<EmployeeEvaluationDto> attendanceList = employeeEvaluationMapper.selectAttendanceEvaluationByEmpNo(empNo);

        return attendanceList;
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 기여도 평가 점수 조회 비즈니스 로직을 수행하는 메소드
     */
    public List<EmployeeEvaluationDto> selectContributionGrade(int empNo) {

        List<EmployeeEvaluationDto> contributionList = employeeEvaluationMapper.selectContributionEvaluationByEmpNo(empNo);

        return contributionList;
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 근태 평가 점수 수정 비즈니스 로직을 수행하는 메소드
     */
    @Transactional
    public Long updateAttendanceGrade(Long empNo, EmployeeEvaluationDto employeeEvaluationDto) {
        // 근태 평가 정보가 존재하는지 확인
        int count = employeeEvaluationMapper.countAttendanceEvaluation(employeeEvaluationDto);

        if (count == 0) {
            return null;
        }

        // 근태 평가 정보 수정
        employeeEvaluationDto.setEmpNo(empNo);

        employeeEvaluationMapper.updateAttendanceGrade(employeeEvaluationDto);

        return employeeEvaluationDto.getEvalAtdNo();
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 기여도 평가 점수 수정 비즈니스 로직을 수행하는 메소드
     */
    @Transactional
    public Long updateContributionGrade(Long empNo, EmployeeEvaluationDto employeeEvaluationDto) {
        // 기여도 평가 정보가 이미 존재하는지 확인
        int count = employeeEvaluationMapper.countContributionEvaluation(employeeEvaluationDto);

        log.info("[EmployeeEvaluationService] count : " + count);

        // 존재하지 않는 경우
        if (count == 0) {
            return null;
        }

        employeeEvaluationMapper.updateContributionGrade(employeeEvaluationDto);

        return employeeEvaluationDto.getEvalContNo();
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 근태 평가 점수 삭제 비즈니스 로직을 수행하는 메소드
     */
    @Transactional
    public String deleteAttendanceGrade(EmployeeEvaluationDto employeeEvaluationDto) {

        List<EmployeeEvaluationDto> employeeEvaluationList = employeeEvaluationMapper.selectAttendanceByEmpNo(employeeEvaluationDto.getEmpNo());

        log.info("[EmployeeEvaluationService] employeeEvaluationList: " + employeeEvaluationList);

        if (employeeEvaluationList == null || employeeEvaluationList.isEmpty()) {
            throw new NotFoundException("존재하지 않는 근태 정보입니다.");
        }

        int result = 0;

        for (EmployeeEvaluationDto evaluation : employeeEvaluationList) {

            Long targetNumber = evaluation.getEmpNo();

            result += employeeEvaluationMapper.deleteAttendanceGradeByEmpNo(targetNumber);
        }
        return (result > 0) ? "삭제 성공" :  "삭제 실패";
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 기여도 평가 점수 삭제 비즈니스 로직을 수행하는 메소드
     */
    @Transactional
    public String deleteContributionGrade(EmployeeEvaluationDto employeeEvaluationDto) {

        // 해당 사원의 기여도 정보 조회
        employeeEvaluationDto = employeeEvaluationMapper.selectContributionByEmpNo(employeeEvaluationDto.getEmpNo());

        log.info("[EmployeeEvaluationService] employeeEvaluationDto: " + employeeEvaluationDto);

        if (employeeEvaluationDto == null) {
            throw new NotFoundException("존재하지 않는 기여도 정보입니다.");
        }

        int result = 0;

        // 삭제할 평가 번호 설정
        Long targetNumber = employeeEvaluationDto.getContNo();

        // 해당 사원의 기여도 평가 정보 삭제
        result = employeeEvaluationMapper.deleteContributionEvaluationByContNo(targetNumber);

        System.out.println("result = " + result);

        return (result > 0) ? "삭제 성공" :  "삭제 실패";
    }

}
