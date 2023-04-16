package com.loung.semof.report.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.SalesDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일이름 : SalesMapper.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이지형
 * @클래스설명 : 영업보고서 crud를 위한 mapper.
 */
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
