package com.loung.semof.humanresource.service;

import com.loung.semof.common.dao.BranchMapper;
import com.loung.semof.common.dao.DepartmentMapper;
import com.loung.semof.common.dao.EmployeeMapper;
import com.loung.semof.common.dto.BranchDto;
import com.loung.semof.common.dto.DepartmentDto;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.humanresource.Exception.NotFoundException;
import com.loung.semof.humanresource.dao.HumanResourceMapper;
import com.loung.semof.humanresource.dto.HumanResourceDto;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @파일이름 : HumanResourceService.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : HumanResourceController에서 요구하는 비즈니스 로직을 수행하는 클래스
 */
@Service
public class HumanResourceService {

    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;
    private final BranchMapper branchMapper;
    private final HumanResourceMapper humanResourceMapper;

    public HumanResourceService(EmployeeMapper employeeMapper, DepartmentMapper departmentMapper, BranchMapper branchMapper, HumanResourceMapper humanResourceMapper) {
        this.employeeMapper = employeeMapper;

        this.departmentMapper = departmentMapper;

        this.branchMapper = branchMapper;

        this.humanResourceMapper = humanResourceMapper;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원의 부서 발령 비즈니스 로직을 수행하는 메소드
     */
    public EmployeeDto updateEmployeeDepartment(Long empNo, String deptCode) throws SQLException, NotFoundException {

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        DepartmentDto department = departmentMapper.selectDepartmentByDeptCode(deptCode);

        if(employee == null) {
            throw new NotFoundException("해당 사원이 존재하지 않습니다.");
        }

        if(department == null) {
            throw new NotFoundException("해당 부서가 존재하지 않습니다.");
        }

        employee.setDeptCode(department.getDeptCode());

        try {
            humanResourceMapper.updateEmployee(employee);

        } catch(Exception e) {
            throw new SQLException("발령 처리 중 오류가 발생했습니다.", e);
        }
        return employee;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원의 지점 발령 비즈니스 로직을 수행하는 메소드.
     */
    public EmployeeDto updateEmployeeBranch(Long empNo, Long branchCode) throws SQLException {

        try {
            EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

            BranchDto branch = branchMapper.selectBranchByBCode(branchCode);

            if (employee == null || branch == null) {
                throw new SQLException("직원의 지점 코드를 수정하지 못하였습니다. 잘못된 사원 번호 혹은  지점 코드 인지 확인해주세요.");
            }

            employee.setBranchCode(branch.getBranchCode());

            humanResourceMapper.updateEmployeeBranch(employee);

            return employee;

        } catch (SQLException e) {
            e.printStackTrace();    // SQLException 처리

            throw e;

        } catch (Exception e) {
            e.printStackTrace();    // 업무 로직에서 발생하는 예외 처리

            throw new RuntimeException("직원의 지점 코드를 수정하지 못했습니다.: " + e.getMessage());
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 등록 비즈니스 로직을 수행하는 메소드.
     */
    public EmployeeDto insertEmployee(EmployeeDto employeeDto) throws SQLException {

        try {
            employeeMapper.insertEmployee(employeeDto);

            return employeeDto;

        } catch (Exception e) {
            e.printStackTrace();

            throw new SQLException("사원 정보를 추가하는 도중 에러가 발생하였습니다.", e);   // 예외 처리 로직
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 정보 수정 비즈니스 로직을 수행하는 메소드
     */
    public EmployeeDto updateEmployee(Long empNo, String phone, String email, String address, Integer salary, Long jobCode) throws Exception {

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        if(employee == null) {
            throw new NotFoundException("해당 사원을 찾을 수 없습니다.");
        }

        if (phone != null) {
            employee.setPhone(phone);
        }
        if (email != null) {
            employee.setEmail(email);
        }
        if (address != null) {
            employee.setAddress(address);
        }
        if (salary != null) {
            employee.setSalary(salary);
        }
        if (jobCode != null) {
            employee.setJobCode(jobCode);
        }

        int affectedRows = employeeMapper.updateEmployee(employee); //update 쿼리가 실행된 결과로, 몇 개의 row가 업데이트 되었는지를 나타내는 변수

        if (affectedRows != 1) {
            throw new SQLException("사원 정보 업데이트에 실패하였습니다.");
        }

        return employee;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 퇴직으로 상태값 변경하는 비즈니스 로직을 수행하는 메소드
     */
    public EmployeeDto updateEmployeeStatus(Long empNo) throws Exception {

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        if(employee != null) {
            employee.setWorkStatus("N");

            int affectedRows = employeeMapper.updateEmployeeStatus(employee);

            if (affectedRows > 0) {
                return employee;

            } else {
                throw new Exception("사원 상태 변경에 실패했습니다.");
            }

        } else {
            throw new NotFoundException("사원을 찾을 수 없습니다.");
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 :  사원의 전체 수를 조회하는 비즈니스 로직을 수행하는 메소드
     */
    public int selectEmployeeTotal() throws SQLException {

        int totalCount = 0;

        try {
            totalCount = humanResourceMapper.selectEmployeeTotal();

        } catch (Exception e) {
            throw new SQLException("전체 직원 수를 조회하지 못했습니다.", e);
        }
        return totalCount;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 전체를 조회해오고 페이지 처리를 하는 비즈니스 로직을 수행하는 메소드
     */
    public List<EmployeeDto> selectEmployeeListWithPaging(int startRow, int endRow) {

        List<EmployeeDto> employeeList = Collections.emptyList();

        try {
            employeeList = humanResourceMapper.selectEmployeeListWithPaging(startRow, endRow);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 조회 비즈니스 로직을 수행하는 메소드
     */
    public EmployeeDto selectEmployee(String empName, String deptCode, Long branchCode) throws Exception {

        EmployeeDto employee = humanResourceMapper.selectEmployee(empName, deptCode, branchCode);

        if (employee == null) {
            throw new NotFoundException("사원을 찾을 수 없습니다.");
        }
        return employee;
    }

    /**
     * @작성일 : 2023-03-22
     * @작성자 : 이현도
     * @메소드설명 : 생일에 해당하는 사원 조회 비즈니스 로직을 수행하는 메소드
     */
    public List<EmployeeDto> selectEmployeeByBirthMonth() throws Exception {

        LocalDate now = LocalDate.now();    // 이번 달 날짜 정보 추출

        int monthValue = now.getMonthValue();

        List<EmployeeDto> employees = humanResourceMapper.selectEmployeeByBirthMonth(monthValue);   // 생일인 사원 조회

        List<EmployeeDto> employeesAfter = humanResourceMapper.selectEmployeeByBirthMonthAfter(monthValue);  // 이번 달 이후 생일인 사원 조회

        employees.sort(Comparator.comparing(e -> e.getEmpReg().substring(2, 6)));   // 생일순으로 정렬

        employeesAfter.sort(Comparator.comparing(e -> e.getEmpReg().substring(2, 6)));

        employees.addAll(employeesAfter);   // 이번 달 생일인 사원과 이번 달 이후 생일인 사원을 합침

        return employees;
    }

    /**
     * @작성일 : 2023-03-23
     * @작성자 : 이현도
     * @메소드설명 : 조직도 출력에 사용 할 사원의 이름을 조회해서 해당 사원의 정보를 출력하는 비즈니스 로직
     */
    public List<HumanResourceDto> searchByEmpName(String empName) {

        if (empName == null || empName.isEmpty()) {
            throw new IllegalArgumentException("사원의 이름을 입력하셔야 합니다.");
        }

        List<HumanResourceDto> result = humanResourceMapper.selectByEmpName(empName);

        if (result.isEmpty()) {
            throw new NotFoundException("해당하는 사원을 찾을 수 없습니다 : " + empName);
        }

        return result;
    }

    /**
     * @작성일 : 2023-03-23
     * @작성자 : 이현도
     * @메소드설명 : 조직도 출력에 사용 할 부서의 이름을 조회해서 해당 부서의 사원 목록을 출력하는 비즈니스 로직
     */
    public List<HumanResourceDto> searchByDeptName(String deptName) {

        if (deptName == null || deptName.isEmpty()) {
            throw new IllegalArgumentException("부서명을 입력하셔야 합니다.");
        }

        List<HumanResourceDto> result = humanResourceMapper.selectByDeptName(deptName);

        if (result.isEmpty()) {
            throw new NotFoundException("해당하는 부서를 찾을 수 없습니다 : " + deptName);
        }

        return result;
    }

    /**
     * @작성일 : 2023-03-23
     * @작성자 : 이현도
     * @메소드설명 : 조직도 출력에 사용 할 지점의 이름을 조회해서 해당 지점의 사원 목록을 출력하는 비즈니스 로직
     */
    public List<HumanResourceDto> searchByBranchName(String branchName) {
        if (branchName == null || branchName.isEmpty()) {
            throw new IllegalArgumentException("지점 이름을 입력해주세요.");
        }

        List<HumanResourceDto> result = humanResourceMapper.selectByBranchName(branchName);

        if (result == null || result.isEmpty()) {
            throw new NotFoundException("일치하는 지점이 없습니다 : " + branchName);
        }

        return result;
    }
}
