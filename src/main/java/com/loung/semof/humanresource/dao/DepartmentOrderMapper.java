package com.loung.semof.humanresource.dao;

import com.loung.semof.humanresource.dto.DepartmentOrderDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentOrderMapper {
    DepartmentOrderDto selectDepartmentOrderByEmpNo(Long empNo);

    void updateDepartmentOrder(DepartmentOrderDto deptOrder);

    void insertDepartmentOrder(DepartmentOrderDto deptOrder);
}
