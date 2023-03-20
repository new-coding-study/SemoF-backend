package com.loung.semof.humanresource.service;

import com.loung.semof.common.dao.DepartmentMapper;
import com.loung.semof.common.dao.EmployeeMapper;
import com.loung.semof.common.dto.DepartmentDto;
import com.loung.semof.common.dto.EmployeeDto;
import org.springframework.stereotype.Service;

@Service
public class HumanResourceService {

    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;

    public HumanResourceService(EmployeeMapper employeeMapper, DepartmentMapper departmentMapper) {
        this.employeeMapper = employeeMapper;
        this.departmentMapper = departmentMapper;
    }

    public boolean updateEmployeeDepartment(Long empNo, String deptCode) {
        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);
        DepartmentDto department = departmentMapper.selectDepartmentByDeptCode(deptCode);

        if(employee != null && department != null) {
            employee.setDeptCode(department.getDeptCode());
            employeeMapper.updateEmployee(employee);
            return true; // 발령 성공인 경우 true를 반환
        }
        return false; // 발령 실패인 경우 false를 반환
    }
}
