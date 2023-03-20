package com.loung.semof.common.dao;

import com.loung.semof.common.dto.DepartmentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
    DepartmentDto selectDepartmentByDeptCode(String deptCode);
}
