package com.loung.semof.report.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.TripDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalesMapper {

    int selectSalesReportTotalForAdmin();
    int selectSalesReportTotalForEmp();
    List<TripDto> selectAllSalesReportForAdminWithPaging(SelectCriteria selectCriteria);
    List<TripDto> selectAllSalesReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo);
//    TripDto detailTripReportForAdmin(Integer tripReportCode);
//    TripDto detailTripReportForEmp(Integer tripReportCode);
    int insertSalesReport(TripDto salesDto);
    int updateSalesReportForAdmin(TripDto salesDto);
    int updateSalesReportForEmp(TripDto salesDto);
    int deleteSalesReportForAdmin(Integer salesReportCode);
    int deleteSalesReportForEmp(Integer salesReportCode);
}
