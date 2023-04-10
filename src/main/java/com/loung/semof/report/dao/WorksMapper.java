package com.loung.semof.report.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.WorksDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WorksMapper {

    int selectWorksReportTotalForAdmin();
    int selectWorksReportTotalForEmp();
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
