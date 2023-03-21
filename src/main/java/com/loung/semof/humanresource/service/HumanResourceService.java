package com.loung.semof.humanresource.service;

import com.loung.semof.common.dao.BranchMapper;
import com.loung.semof.common.dao.DepartmentMapper;
import com.loung.semof.common.dao.EmployeeMapper;
import com.loung.semof.common.dto.BranchDto;
import com.loung.semof.common.dto.DepartmentDto;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.humanresource.Exception.NotFoundException;
import com.loung.semof.humanresource.dao.HumanResourceMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
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
    public boolean updateEmployeeDepartment(Long empNo, String deptCode) {

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        DepartmentDto department = departmentMapper.selectDepartmentByDeptCode(deptCode);

        if(employee != null && department != null) {
            employee.setDeptCode(department.getDeptCode());

            humanResourceMapper.updateEmployee(employee);

            return true; // 발령 성공인 경우 true를 반환
        }
        return false; // 발령 실패인 경우 false를 반환
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원의 지점 발령 비즈니스 로직을 수행하는 메소드.
     */
    public boolean updateEmployeeBranch(Long empNo, Long branchCode) {

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        BranchDto branch = branchMapper.selectBranchByBCode(branchCode);

        if(employee != null && branch != null) {
            employee.setBranchCode(branch.getBranchCode());

            humanResourceMapper.updateEmployeeBranch(employee);

            return true; // 발령 성공인 경우 true를 반환
        }
        return false; // 발령 실패인 경우 false를 반환
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

            throw new SQLException("사원 정보를 추가하는 도중 에러가 발생하였습니다.", e);   // 예외 처리 로직
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 정보 수정 비즈니스 로직을 수행하는 메소드
     */
    public boolean updateEmployee(Long empNo, String phone, String email, String address, Integer salary, Long jobCode) {

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        if(employee != null) {
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

            employeeMapper.updateEmployee(employee);

            return true; // 수정 성공인 경우 true를 반환
        }
        return false; // 수정 실패인 경우 false를 반환
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 퇴직으로 상태값 변경하는 비즈니스 로직을 수행하는 메소드
     */
    public boolean updateEmployeeStatus(Long empNo) {

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        if(employee != null) {

            employeeMapper.updateEmployeeStatus(employee);

            return true; // 수정 성공인 경우 true를 반환
        }
        return false; // 수정 실패인 경우 false를 반환
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
     * @메소드설명 : 설명을 여기에 작성한다.
     */
    public EmployeeDto selectEmployeeByEmpName(String empName) throws Exception {

        EmployeeDto employee = humanResourceMapper.selectEmployeeByEmpName(empName);

        if (employee == null) {
            throw new NotFoundException("사원을 찾을 수 없습니다.");
        }
        return employee;
    }
}
