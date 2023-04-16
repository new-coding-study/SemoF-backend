package com.loung.semof.report.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dao.WorksMapper;
import com.loung.semof.report.dto.WorksDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @파일이름 : WorksService.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-05
 * @작성자 : 이지형
 * @클래스설명 : 업무보고서 crud를 위한 service.
 */
@Service
public class WorksService {

    private final WorksMapper worksMapper;

    public WorksService(WorksMapper worksMapper) {
        this.worksMapper = worksMapper;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 관리자를 위한 보고서 전체 갯수를 조회.
     */
    public int selectWorksReportTotalForAdmin() {
        int result = worksMapper.selectWorksReportTotalForAdmin();
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllWorksReportForAdminWithPaging(SelectCriteria selectCriteria) {
        List<WorksDto> worksDto = worksMapper.selectAllWorksReportForAdminWithPaging(selectCriteria);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽지 않은 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllWorkNStatusForAdmin(SelectCriteria selectCriteria) {
        List<WorksDto> worksDto = worksMapper.selectAllWorkNStatusForAdmin(selectCriteria);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽은 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllWorkYStatusForAdmin(SelectCriteria selectCriteria) {
        List<WorksDto> worksDto = worksMapper.selectAllWorkYStatusForAdmin(selectCriteria);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 직원를 위한 보고서 전체 갯수를 조회.
     */
    public int selectWorksReportTotalForEmp() {
        int result = worksMapper.selectWorksReportTotalForEmp();
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllWorksReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo) {
        List<WorksDto> worksDto = worksMapper.selectAllWorksReportForEmpWithPaging(selectCriteria, empNo);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽지 않은 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllWorkNStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<WorksDto> worksDto = worksMapper.selectAllWorkNStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽은 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllWorkYStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<WorksDto> worksDto = worksMapper.selectAllWorkYStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 상세 조회(관리자).
     */
    public Object detailWorksReportForAdmin(Integer worksReportCode) {
        WorksDto worksDto = worksMapper.selectDetailWorksForAdmin(worksReportCode);
        return worksDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 상세 조회(직원).
     */
    public Object detailWorksReportForEmp(Integer worksReportCode) {
        WorksDto worksDto = worksMapper.selectDetailWorksForEmp(worksReportCode);
        return worksDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 등록을 위한 메소드.
     */
    @Transactional
    public Object insertWorksReport(WorksDto worksDto) {
        System.out.println("worksDto = " + worksDto);
        int result = worksMapper.insertWorksReport(worksDto);
        System.out.println("result = " + result);
        return (result > 0)? "등록성공":"등록실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서에 comment를 작성하기 메소드(관리자).
     */
    @Transactional
    public Object updateWorksReportForAdmin(WorksDto worksDto) {
        int result = worksMapper.updateWorksReportForAdmin(worksDto);
        return (result > 0)? "의견 작성 성공":"작성 실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서에 내용을 수정하기 위한 메소드.
     */
    @Transactional
    public Object updateWorksReportForEmp(WorksDto worksDto) {
        int result = worksMapper.updateWorksReportForEmp(worksDto);
        return (result > 0)? "등록성공":"등록실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서를 삭제하기 위한 메소드(관리자).
     */
    @Transactional
    public Object deleteWorksReportForAdmin(Integer worksReportCode) {
        int result = worksMapper.deleteWorksReportForAdmin(worksReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서를 삭제하기 위한 메소드(직원).
     */
    @Transactional
    public Object deleteWorksReportForEmp(Integer worksReportCode) {
        int result = worksMapper.deleteWorksReportForEmp(worksReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }


}
