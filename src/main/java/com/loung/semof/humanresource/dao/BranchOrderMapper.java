package com.loung.semof.humanresource.dao;

import com.loung.semof.humanresource.dto.BranchOrderDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * @파일이름 : BranchOrderMapper.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-31
 * @작성자 : 이현도
 * @클래스설명 : 비즈니스로직과 데이터베이스간의 상호작용을 수행하는 클래스
 */
@Mapper
public interface BranchOrderMapper {
    BranchOrderDto selectBranchOrderById(Long empNo);
    void updateBranchOrder(BranchOrderDto branchOrder);

    void insertBranchOrder(BranchOrderDto branchOrder);
}
