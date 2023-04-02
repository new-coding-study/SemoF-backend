package com.loung.semof.humanresource.dao;

import com.loung.semof.humanresource.dto.BranchOrderDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BranchOrderMapper {
    BranchOrderDto selectBranchOrderById(Long empNo);
    void updateBranchOrder(BranchOrderDto branchOrder);

    void insertBranchOrder(BranchOrderDto branchOrder);
}
