package com.loung.semof.humanresource.controller;

import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.humanresource.service.HumanResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @파일이름 : HumanResourceController.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : 인사관리와 관련된 뷰의 명령을 실행하는 프로그램
 */
@Slf4j
@RestController
@RequestMapping("/api/employees")
public class HumanResourceController {

    private final HumanResourceService humanResourceService;
    public HumanResourceController(HumanResourceService humanResourceService) {
        this.humanResourceService = humanResourceService;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원의 부서 발령을 수행하는 메소드
     */
    @PutMapping("/department")
    public ResponseEntity<ResponseDto> updateEmployeeDepartment(@RequestBody EmployeeDto employeeDto) {
        boolean isSuccess = humanResourceService.updateEmployeeDepartment(employeeDto.getEmpNo(), employeeDto.getDeptCode());
        if (isSuccess) {
            return ResponseEntity.ok()
                    .body(new ResponseDto(HttpStatus.OK, "발령 성공", isSuccess));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "발령 실패", isSuccess));
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원의 지점 발령을 수행하는 메소드
     */
    @PutMapping("/branch")
    public ResponseEntity<ResponseDto> updateEmployeeBranch(@RequestBody EmployeeDto employeeDto) {
        boolean isSuccess = humanResourceService.updateEmployeeBranch(employeeDto.getEmpNo(), employeeDto.getBranchCode());
        if (isSuccess) {
            return ResponseEntity.ok()
                    .body(new ResponseDto(HttpStatus.OK, "발령 성공", isSuccess));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "발령 실패", isSuccess));
        }
    }
}
