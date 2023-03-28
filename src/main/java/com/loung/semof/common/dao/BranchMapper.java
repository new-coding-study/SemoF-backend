package com.loung.semof.common.dao;

import com.loung.semof.common.dto.BranchDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * @파일이름 : BranchMapper.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : 비즈니스로직과 데이터베이스간의 상호작용을 수행하는 클래스
 */
@Mapper
public interface BranchMapper {
    BranchDto selectBranchByBCode(Long branchCode);
}
