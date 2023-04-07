package com.loung.semof.report.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dao.SalesMapper;
import com.loung.semof.report.dto.SalesDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalesService {

    private final SalesMapper salesMapper;

    public SalesService(SalesMapper salesMapper) {
        this.salesMapper = salesMapper;
    }

    public int selectSalesReportTotalForAdmin() {
        int result = salesMapper.selectSalesReportTotalForAdmin();
        return result;
    }

    public Object selectAllSalesReportForAdminWithPaging(SelectCriteria selectCriteria) {
        List<SalesDto> salesDtoList = salesMapper.selectAllSalesReportForAdminWithPaging(selectCriteria);
        for (int i = 0; i<salesDtoList.size(); i++);
        return salesDtoList;
    }

    public int selectSalesReportTotalForEmp() {
        int result = salesMapper.selectSalesReportTotalForEmp();
        return result;
    }

    public Object selectAllSalesReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo) {
        List<SalesDto> salesDtoList = salesMapper.selectAllSalesReportForEmpWithPaging(selectCriteria, empNo);
        for (int i = 0; i<salesDtoList.size(); i++);
        return salesDtoList;
    }


    public Object detailSalesReportForAdmin(Integer salesReportCode) {
        SalesDto salesDto = salesMapper.detailSalesReportForAdmin(salesReportCode);
        return salesDto;
    }

    public Object detailSalesReportForEmp(Integer salesReportCode) {
        SalesDto salesDto = salesMapper.detailSalesReportForEmp(salesReportCode);
        return salesDto;
    }

    @Transactional
    public Object insertSalesReport(SalesDto salesDto) {
        int result = salesMapper.insertSalesReport(salesDto);
        return (result > 0)? "등록성공":"등록실패";
    }

    @Transactional
    public Object updateSalesReportForAdmin(SalesDto salesDto) {
        int result = salesMapper.updateSalesReportForAdmin(salesDto);
        return (result > 0)? "수정 성공":"수정 실패";
    }

    @Transactional
    public Object updateSalesReportForEmp(SalesDto salesDto) {
        int result = salesMapper.updateSalesReportForEmp(salesDto);
        return (result > 0)? "수정 성공":"수정 실패";
    }

    @Transactional
    public Object deleteSalesReportForAdmin(Integer salesReportCode) {
        int result = salesMapper.deleteSalesReportForAdmin(salesReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

    @Transactional
    public Object deleteSalesReportForEmp(Integer salesReportCode) {
        int result = salesMapper.deleteSalesReportForEmp(salesReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }
}
