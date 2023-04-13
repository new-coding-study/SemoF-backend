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

    List<TripDto> selectAllTripNStatusForAdmin(SelectCriteria selectCriteria);

    List<TripDto> selectAllTripYStatusForAdmin(SelectCriteria selectCriteria);

    List<TripDto> selectAllTripNStatusForEmp(SelectCriteria selectCriteria, int empNo);

    List<TripDto> selectAllTripYStatusForEmp(SelectCriteria selectCriteria, int empNo);
}
