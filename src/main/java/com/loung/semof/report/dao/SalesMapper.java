package com.loung.semof.report.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.SalesDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalesMapper {

    int selectSalesReportTotalForAdmin();
    int selectSalesReportTotalForEmp();
    List<SalesDto> selectAllSalesReportForAdminWithPaging(SelectCriteria selectCriteria);
    List<SalesDto> selectAllSalesReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo);
    SalesDto detailSalesReportForAdmin(Integer salesReportCode);
    SalesDto detailSalesReportForEmp(Integer salesReportCode);
    int insertSalesReport(SalesDto salesDto);
    int updateSalesReportForAdmin(SalesDto salesDto);
    int updateSalesReportForEmp(SalesDto salesDto);
    int deleteSalesReportForAdmin(Integer salesReportCode);
    int deleteSalesReportForEmp(Integer salesReportCode);

    List<SalesDto> selectAllSalesNStatusForAdmin(SelectCriteria selectCriteria);

    List<SalesDto> selectAllSalesYStatusForAdmin(SelectCriteria selectCriteria);

    List<SalesDto> selectAllSalesNStatusForEmp(SelectCriteria selectCriteria, int empNo);

    List<SalesDto> selectAllSalesYStatusForEmp(SelectCriteria selectCriteria, int empNo);
}
