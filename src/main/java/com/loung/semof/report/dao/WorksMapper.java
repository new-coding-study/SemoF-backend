package com.loung.semof.report.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.WorksDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일이름 : WorksMapper.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이지형
 * @클래스설명 : 업무보고서 crud를 위한 mapper.
 */
@Mapper
public interface WorksMapper {

    int selectWorksReportTotalForAdmin();
    int selectWorksReportTotalForEmp(int empNo);
    List<WorksDto> selectAllWorksReportForAdminWithPaging(SelectCriteria selectCriteria);
    List<WorksDto> selectAllWorkNStatusForAdmin(SelectCriteria selectCriteria);
    List<WorksDto> selectAllWorkYStatusForAdmin(SelectCriteria selectCriteria);
    List<WorksDto> selectAllWorksReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo);
    List<WorksDto> selectAllWorkNStatusForEmp(SelectCriteria selectCriteria, int empNo);
    List<WorksDto> selectAllWorkYStatusForEmp(SelectCriteria selectCriteria, int empNo);
    WorksDto selectDetailWorksForAdmin(Integer worksReportCode);
    WorksDto selectDetailWorksForEmp(Integer worksReportCode);
    int insertWorksReport(WorksDto worksDto);
    int updateWorksReportForAdmin(WorksDto worksDto);
    int updateWorksReportForEmp(WorksDto worksDto);
    int deleteWorksReportForAdmin(Integer worksReportCode);
    int deleteWorksReportForEmp(Integer worksReportCode);

}
