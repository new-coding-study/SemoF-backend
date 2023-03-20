package com.loung.semof.common.dao;

import com.loung.semof.common.dto.EmployeeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
    void updateEmployee(EmployeeDto employee);

    EmployeeDto selectEmployeeByEmpNo(Long empNo);
}