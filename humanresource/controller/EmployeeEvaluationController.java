package com.loung.semof.humanresource.controller;


import com.loung.semof.common.ResponseDto;
import com.loung.semof.humanresource.dto.EmployeeEvaluationDto;
import com.loung.semof.humanresource.service.EmployeeEvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeEvaluationController {

    private final EmployeeEvaluationService employeeEvaluationService;

    public EmployeeEvaluationController(EmployeeEvaluationService employeeEvaluationService) {
        this.employeeEvaluationService = employeeEvaluationService;
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 월별 출근, 결근율을 조회하는 메소드
     */
    @GetMapping("/attendances/{empNo}")
    public ResponseEntity<ResponseDto> selectAttendanceSummary(@PathVariable("empNo") int empNo) {

        Map<String, Object> summary = employeeEvaluationService.selectAttendanceSummary(empNo);

        log.info("[EmployeeEvaluationController] summary: " + summary);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", summary));
    }
    
    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원번호에 해당하는 프로젝트 기여도를와 평균을 조회하는 메소드
     */
    @GetMapping("/contributions/{empNo}")
    public ResponseEntity<ResponseDto> selectProjectContributionRates(@PathVariable Long empNo) {

        List<EmployeeEvaluationDto> employeeEvaluationDtoList = employeeEvaluationService.selectProjectContributionRates(empNo);

        log.info("[EmployeeEvaluationController] employeeEvaluationDtoList" + employeeEvaluationDtoList);

        if (employeeEvaluationDtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(HttpStatus.NOT_FOUND, "해당 직원이 존재하지 않습니다.", null));
        }

        log.info("[EmployeeEvaluationController] EmployeeEvaluationDtoList : " + employeeEvaluationDtoList);
        return ResponseEntity.ok()
                .body(new ResponseDto(HttpStatus.OK, "조회 성공", employeeEvaluationDtoList));
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 카테고리를 선택하고, 선택에 따라서 근태평가, 기여도 평가를 입력하는 메소드
     */
    @PostMapping("/evaluation")
    public ResponseEntity<ResponseDto> insertGrade(@RequestBody EmployeeEvaluationDto employeeEvaluationDto) {

        log.info("[EmployeeEvaluationController] EmployeeEvaluationDto : " + employeeEvaluationDto);

        try {
            Long insertedCategory = null;

            if (employeeEvaluationDto.getCategoryNo() == 1) {
                insertedCategory = employeeEvaluationService.insertAttendanceGrade(employeeEvaluationDto);
            } else if (employeeEvaluationDto.getCategoryNo() == 2) {
                insertedCategory = employeeEvaluationService.insertContributionGrade(employeeEvaluationDto);
            } else {
                return ResponseEntity.badRequest().body(new ResponseDto(HttpStatus.BAD_REQUEST, "카테고리 번호를 다시 확인해주세요.", null));
            }

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "평가 등록 성공", employeeEvaluationDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 근태 평가 점수를 조회하는 메소드
     */
    @GetMapping("/evaluation/attendance/{empNo}")
    public ResponseEntity<ResponseDto> selectAttendanceGrade(@PathVariable("empNo") int empNo) {
        
        try {
            List<EmployeeEvaluationDto> attendanceList = employeeEvaluationService.selectAttendanceGrade(empNo);

            log.info("[EmployeeEvaluationController] attendanceList : " + attendanceList);

            if (attendanceList == null || attendanceList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(HttpStatus.NOT_FOUND, "근태 평가 정보가 존재하지 않습니다.", null));
            }

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원의 근태 평가 조회 성공", attendanceList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "사원의 근태 평가 조회 중 오류가 발생했습니다.", null));
        }
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 기여도 평가 점수를 조회하는 메소드
     */
    @GetMapping("/evaluation/contributions/{empNo}")
    public ResponseEntity<ResponseDto> selectContributionGrade(@PathVariable("empNo") int empNo) {
        try {
            List<EmployeeEvaluationDto> contributionList = employeeEvaluationService.selectContributionGrade(empNo);

            log.info("[EmployeeEvaluationController] contributionList : " + contributionList);

            if (contributionList == null || contributionList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(HttpStatus.NOT_FOUND, "존재하지 않는 기여도 평가 정보입니다.", null));
            }

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원의 기여도 평가 조회 성공", contributionList));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "사원의 기여도 평가 조회 중 오류가 발생했습니다.", null));
        }
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 근태 평가 점수를 수정하는 메소드
     */
    @PutMapping("/evaluation/attendances/{empNo}")
    public ResponseEntity<ResponseDto> updateAttendanceGrade(@PathVariable("empNo") Long empNo
            , @RequestBody EmployeeEvaluationDto employeeEvaluationDto) {

        try {

            // RequestBody로 받은 employeeEvaluationDto에 empNo 설정
            employeeEvaluationDto.setEmpNo(empNo);

            Long updatedAttendance = employeeEvaluationService.updateAttendanceGrade(empNo, employeeEvaluationDto);

            log.info("[EmployeeEvaluationController] updatedAttendance :" + updatedAttendance);

            if (updatedAttendance == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(HttpStatus.NOT_FOUND, "존재하지 않는 근태 평가 정보입니다.", null));
            }

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원의 근태 평가 수정 성공", employeeEvaluationDto));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "사원의 근태 평가 수정 중 오류가 발생했습니다.", null));
        }
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 :  사원의 기여도 평가 점수를 수정하는 메소드
     */
    @PutMapping("/evaluation/contributions/{empNo}")
    public ResponseEntity<ResponseDto> updateContributionGrade(@PathVariable("empNo") Long empNo,
                                                               @RequestBody EmployeeEvaluationDto employeeEvaluationDto) {
        try {

            // RequestBody로 받은 employeeEvaluationDto에 empNo 설정
            employeeEvaluationDto.setEmpNo(empNo);

            Long updatedContribution = employeeEvaluationService.updateContributionGrade(empNo, employeeEvaluationDto);

            log.info("[EmployeeEvaluationController] updatedContribution :" + updatedContribution);

            if (updatedContribution == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(HttpStatus.NOT_FOUND, "존재하지 않는 기여도 평가 정보입니다.", null));
            }

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "사원의 기여도 평가 수정 성공", employeeEvaluationDto));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "사원의 기여도 평가 수정 중 오류가 발생했습니다.", null));
        }
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 근태 평가 점수를 삭제하는 메소드
     */
    @DeleteMapping("/evaluation/attendances/{empNo}")
    public ResponseEntity<ResponseDto> deleteAttendanceGrade(@PathVariable("empNo") Long empNo) {

        try {

            EmployeeEvaluationDto employeeEvaluationDto = new EmployeeEvaluationDto();

            // employeeEvaluationDto에 empNo 설정
            employeeEvaluationDto.setEmpNo(empNo);

            log.info("[EmployeeEvaluationController] employeeEvaluationDto" + employeeEvaluationDto);

            return ResponseEntity.ok()
                    .body(new ResponseDto(HttpStatus.OK, "근태 평가 정보 삭제 성공", employeeEvaluationService.deleteAttendanceGrade(employeeEvaluationDto)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "근태 평가 정보 삭제 중 오류가 발생했습니다.", null));
        }
    }

    /**
     * @작성일 : 2023-03-26
     * @작성자 : 이현도
     * @메소드설명 : 사원의 기여도 평가 점수를 삭제하는 메소드
     */
    @DeleteMapping("/evaluation/contributions/{empNo}")
    public ResponseEntity<ResponseDto> deleteContributionGrade(@PathVariable("empNo") Long empNo) {

        try {

            EmployeeEvaluationDto employeeEvaluationDto = new EmployeeEvaluationDto();

            // employeeEvaluationDto에 empNo 설정
            employeeEvaluationDto.setEmpNo(empNo);

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "기여도 평가 정보 삭제 성공", employeeEvaluationService.deleteContributionGrade(employeeEvaluationDto)));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "기여도 평가 정보 삭제 중 오류가 발생했습니다.", null));
        }
    }
}
