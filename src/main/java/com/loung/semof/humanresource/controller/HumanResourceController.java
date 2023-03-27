package com.loung.semof.humanresource.controller;

import com.loung.semof.common.ResponseDto;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.common.paging.Pagenation;
import com.loung.semof.common.paging.ResponseDtoWithPaging;
import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.humanresource.Exception.NotFoundException;
import com.loung.semof.humanresource.dto.HumanResourceDto;
import com.loung.semof.humanresource.service.HumanResourceService;
import com.loung.semof.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @파일이름 : HumanResourceController.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : 인사관리와 관련된 뷰의 명령을 실행하는 클래스
 */
@Slf4j
@RestController
@RequestMapping("/employees")
public class HumanResourceController {

    private final HumanResourceService humanResourceService;
    private final TodoService todoService;

    public HumanResourceController(HumanResourceService humanResourceService, TodoService todoService) {
        this.humanResourceService = humanResourceService;
        this.todoService = todoService;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원의 부서 발령을 수행하는 메소드
     */
    @PutMapping("/departments")
    public ResponseEntity<ResponseDto> updateEmployeeDepartment(@RequestBody EmployeeDto employeeDto) {

        try {
            EmployeeDto employee = humanResourceService.updateEmployeeDepartment(employeeDto.getEmpNo(), employeeDto.getDeptCode());

            if (employee != null) {
                return ResponseEntity.ok()
                        .body(new ResponseDto(HttpStatus.OK, "발령 성공", employee));

            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "발령 실패", null));
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류", null));
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원의 지점 발령을 수행하는 메소드
     */
    @PutMapping("/branches")
    public ResponseEntity<ResponseDto> updateEmployeeBranch(@RequestBody EmployeeDto employeeDto) {

        try {
            EmployeeDto employee = humanResourceService.updateEmployeeBranch(employeeDto.getEmpNo(), employeeDto.getBranchCode());

            if (employee != null) {
                return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "발령 성공", employee));

            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "발령 실패", null));
            }

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 등록을 수행하는 메소드
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> insertEmployee(@ModelAttribute EmployeeDto employeeDto,
                                                      @RequestPart(value = "employeePhoto", required = false) MultipartFile employeePhoto) throws SQLException {
        try {
            EmployeeDto employee = humanResourceService.insertEmployee(employeeDto);

            log.info("[HumanResourceController] employee" + employee);

            humanResourceService.insertEmployeePhoto(employeePhoto, employee);

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "사원등록 성공", employee));
        } catch (SQLException | IOException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 정보 수정을 수행하는 메소드
     */
    @PutMapping("/present")
    public ResponseEntity<ResponseDto> updateEmployee(@ModelAttribute EmployeeDto employeeDto,
                                                      @RequestPart(value = "employeePhoto", required = false) MultipartFile employeePhoto) {
        try {
            EmployeeDto employee = humanResourceService.updateEmployee(employeeDto.getEmpNo(),
                    employeeDto.getPhone(), employeeDto.getEmail(),
                    employeeDto.getAddress(), employeeDto.getSalary(),
                    employeeDto.getJobCode(), employeePhoto);

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "수정 성공", employee));

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "수정 실패", null));
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 상태값을 변경하여 퇴사 처리를 수행하는 메소드
     */
    @DeleteMapping("/present")
    public ResponseEntity<ResponseDto> updateEmployeeStatus(@RequestBody EmployeeDto employeeDto) {

        try {
            EmployeeDto employee = humanResourceService.updateEmployeeStatus(employeeDto.getEmpNo());

            if (employee != null) {
                return ResponseEntity.ok()
                        .body(new ResponseDto(HttpStatus.OK, "수정 성공", employee));

            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "수정 실패", null));
            }

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(HttpStatus.NOT_FOUND, "해당 사원을 찾을 수 없습니다.", null));

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 발생", null));
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 현직 전체 사원의 조회 처리를 수행하는 메소드
     */
    @GetMapping("/all")
    public ResponseEntity<ResponseDto> selectEmployeeListWithPaging(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo) throws SQLException {

        try {
            int totalCount = humanResourceService.selectEmployeeTotal();

            int limit = 10;

            int buttonAmount = 5;

            SelectCriteria selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);

            List<EmployeeDto> employees = humanResourceService.selectEmployeeListWithPaging(selectCriteria.getStartRow(), selectCriteria.getEndRow());

            ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();

            responseDtoWithPaging.setPageInfo(selectCriteria);

            responseDtoWithPaging.setData(employees);

            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));

        } catch (SQLException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null));
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원을 조건으로 조회하는 메소드
     */
    @GetMapping("/present")
    public ResponseEntity<ResponseDto> selectEmployee(@RequestParam(required = false) String empName,
                                                      @RequestParam(required = false) String deptCode,
                                                      @RequestParam(required = false) Long branchCode) throws Exception {

        try {
            List<EmployeeDto> employees = humanResourceService.selectEmployees(empName, deptCode, branchCode);

            log.info(" [HumanResourceController] Employees: " + employees);

            if (!employees.isEmpty()) {
                return ResponseEntity.ok()
                        .body(new ResponseDto(HttpStatus.OK, "조회 성공", employees));

            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null));
            }

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null));
        }
    }

    /**
     * @작성일 : 2023-03-22
     * @작성자 : 이현도
     * @메소드설명 : 생일인 사원을 조회하는 메소드
     */
    @GetMapping("/birthday")
    public ResponseEntity<ResponseDto> selectEmployeeByBirthMonth() {

        try {
            List<EmployeeDto> employees = humanResourceService.selectEmployeeByBirthMonth();

            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDto(HttpStatus.NOT_FOUND, "조회 실패 - 생일인 사원이 존재하지 않습니다.", new ArrayList<EmployeeDto>()));
            } else {
                return ResponseEntity.ok()
                        .body(new ResponseDto(HttpStatus.OK, "조회 성공", employees));
            }

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null));
        }
    }

    /**
     * @작성일 : 2023-03-23
     * @작성자 : 이현도
     * @메소드설명 : 조직도를 위한 조건 검색 메소드
     */
    @GetMapping("/chart")
    public ResponseEntity<ResponseDto> SelectEmployeesForChart(@RequestParam(required = false) String empName,
                                         @RequestParam(required = false) String deptName,
                                         @RequestParam(required = false) String branchName) {

        try {
            List<HumanResourceDto> employees = humanResourceService.SelectEmployeesForChart(empName, deptName, branchName);

            log.info("[HumanResourceController] Employees: " + employees);

            if (!employees.isEmpty()) {
                return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "조회 성공", employees));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null));
            }

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "조회 실패", null));
        }
    }

    /**
     * @작성일 : 2023-03-24
     * @작성자 : 이현도
     * @메소드설명 : 사원 번호로 사원을 조회하는 메소드.
     */
    @GetMapping("/present/{empNo}")
    public ResponseEntity<ResponseDto> selectEmployeeByEmpNo(@PathVariable("empNo") Long empNo) {

        EmployeeDto employee = humanResourceService.selectEmployeeByEmpNo(empNo);

        if (employee != null) {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", employee));
            
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(HttpStatus.NOT_FOUND, "조회 실패", null));
        }
    }
}