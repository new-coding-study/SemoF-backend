package com.loung.semof.report.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.TripDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripMapper {

    int selectTripReportTotalForAdmin();
    int selectTripReportTotalForEmp();
    List<TripDto> selectAllTripReportForAdminWithPaging(SelectCriteria selectCriteria);
    List<TripDto> selectAllTripReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo);
    TripDto detailTripReportForAdmin(Integer tripReportCode);
    TripDto detailTripReportForEmp(Integer tripReportCode);
    int insertTripReport(TripDto tripDto);
    int updateTripReportForAdmin(TripDto tripDto);
    int updateTripReportForEmp(TripDto tripDto);
    int deleteTripReportForAdmin(Integer tripReportCode);
    int deleteTripReportForEmp(Integer tripReportCode);

}
