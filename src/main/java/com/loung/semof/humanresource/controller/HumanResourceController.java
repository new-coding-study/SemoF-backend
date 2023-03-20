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

@Slf4j
@RestController
@RequestMapping("/api/employees")
public class HumanResourceController {

    private final HumanResourceService humanResourceService;
    public HumanResourceController(HumanResourceService humanResourceService) {
        this.humanResourceService = humanResourceService;
    }

//    @PutMapping("/department")
//    public ResponseEntity<ResponseDto> updateEmployeeDepartment(@RequestParam("empNo") Long empNo
//            , @RequestParam("deptCode") String deptCode) {
//
//        boolean isSuccess = humanResourceService.updateEmployeeDepartment(empNo, deptCode);
//        if (isSuccess) {
//            return ResponseEntity.ok()
//                    .body(new ResponseDto(HttpStatus.OK, "발령 성공", isSuccess));
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "발령 실패", isSuccess));
//        }
//    }

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
}
