package com.loung.semof.report.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dao.SalesMapper;
import com.loung.semof.report.dto.SalesDto;
import com.loung.semof.report.dto.WorksDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @파일이름 : SalesService.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-05
 * @작성자 : 이지형
 * @클래스설명 : 영업보고서 crud를 위한 service.
 */
@Service
public class SalesService {

    private final SalesMapper salesMapper;

    public SalesService(SalesMapper salesMapper) {
        this.salesMapper = salesMapper;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 관리자를 위한 보고서 전체 갯수를 조회.
     */
    public int selectSalesReportTotalForAdmin() {
        int result = salesMapper.selectSalesReportTotalForAdmin();
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllSalesReportForAdminWithPaging(SelectCriteria selectCriteria) {
        List<SalesDto> salesDtoList = salesMapper.selectAllSalesReportForAdminWithPaging(selectCriteria);
        for (int i = 0; i<salesDtoList.size(); i++);
        return salesDtoList;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽지 않은 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllSalesNStatusForAdmin(SelectCriteria selectCriteria) {
        List<SalesDto> salesDto = salesMapper.selectAllSalesNStatusForAdmin(selectCriteria);
        for(int i = 0; i<salesDto.size(); i++);
        return salesDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽은 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllSalesYStatusForAdmin(SelectCriteria selectCriteria) {
        List<SalesDto> salesDto = salesMapper.selectAllSalesYStatusForAdmin(selectCriteria);
        for(int i = 0; i<salesDto.size(); i++);
        return salesDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 직원를 위한 보고서 전체 갯수를 조회.
     */
    public int selectSalesReportTotalForEmp(int empNo) {
        int result = salesMapper.selectSalesReportTotalForEmp(empNo);
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllSalesReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo) {
        List<SalesDto> salesDtoList = salesMapper.selectAllSalesReportForEmpWithPaging(selectCriteria, empNo);
        for (int i = 0; i<salesDtoList.size(); i++);
        return salesDtoList;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽지 않은 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllSalesNStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<SalesDto> salesDto = salesMapper.selectAllSalesNStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<salesDto.size(); i++);
        return salesDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽은 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllSalesYStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<SalesDto> salesDto = salesMapper.selectAllSalesYStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<salesDto.size(); i++);
        return salesDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 상세 조회(관리자).
     */
    public Object detailSalesReportForAdmin(Integer salesReportCode) {
        SalesDto salesDto = salesMapper.detailSalesReportForAdmin(salesReportCode);
        return salesDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 상세 조회(직원).
     */
    public Object detailSalesReportForEmp(Integer salesReportCode) {
        SalesDto salesDto = salesMapper.detailSalesReportForEmp(salesReportCode);
        return salesDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 등록을 위한 메소드.
     */
    @Transactional
    public Object insertSalesReport(SalesDto salesDto) {
        int result = salesMapper.insertSalesReport(salesDto);
        return (result > 0)? "등록성공":"등록실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서에 comment를 작성하기 메소드(관리자).
     */
    @Transactional
    public Object updateSalesReportForAdmin(SalesDto salesDto) {
        int result = salesMapper.updateSalesReportForAdmin(salesDto);
        return (result > 0)? "의견작성 성공":"작성 실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서에 내용을 수정하기 위한 메소드.
     */
    @Transactional
    public Object updateSalesReportForEmp(SalesDto salesDto) {
        int result = salesMapper.updateSalesReportForEmp(salesDto);
        return (result > 0)? "수정 성공":"수정 실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서를 삭제하기 위한 메소드(관리자).
     */
    @Transactional
    public Object deleteSalesReportForAdmin(Integer salesReportCode) {
        int result = salesMapper.deleteSalesReportForAdmin(salesReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서를 삭제하기 위한 메소드(직원).
     */
    @Transactional
    public Object deleteSalesReportForEmp(Integer salesReportCode) {
        int result = salesMapper.deleteSalesReportForEmp(salesReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

}
