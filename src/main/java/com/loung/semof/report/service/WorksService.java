package com.loung.semof.report.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dao.WorksMapper;
import com.loung.semof.report.dto.WorksDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorksService {

    private final WorksMapper worksMapper;

    public WorksService(WorksMapper worksMapper) {
        this.worksMapper = worksMapper;
    }
    public int selectWorksReportTotalForAdmin() {
        int result = worksMapper.selectWorksReportTotalForAdmin();
        return result;
    }


    public Object selectAllWorksReportForAdminWithPaging(SelectCriteria selectCriteria) {
        List<WorksDto> worksDto = worksMapper.selectAllWorksReportForAdminWithPaging(selectCriteria);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }

    public Object selectAllWorkNStatusForAdmin(SelectCriteria selectCriteria) {
        List<WorksDto> worksDto = worksMapper.selectAllWorkNStatusForAdmin(selectCriteria);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }
    public Object selectAllWorkYStatusForAdmin(SelectCriteria selectCriteria) {
        List<WorksDto> worksDto = worksMapper.selectAllWorkYStatusForAdmin(selectCriteria);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }
    public int selectWorksReportTotalForEmp() {
        int result = worksMapper.selectWorksReportTotalForEmp();
        return result;
    }

    public Object selectAllWorksReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo) {
        List<WorksDto> worksDto = worksMapper.selectAllWorksReportForEmpWithPaging(selectCriteria, empNo);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }
    public Object selectAllWorkNStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<WorksDto> worksDto = worksMapper.selectAllWorkNStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }
    public Object selectAllWorkYStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<WorksDto> worksDto = worksMapper.selectAllWorkYStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<worksDto.size(); i++);
        return worksDto;
    }

    public Object detailWorksReportForAdmin(Integer worksReportCode) {
        WorksDto worksDto = worksMapper.selectDetailWorksForAdmin(worksReportCode);
        return worksDto;
    }

    public Object detailWorksReportForEmp(Integer worksReportCode) {
        WorksDto worksDto = worksMapper.selectDetailWorksForEmp(worksReportCode);
        return worksDto;
    }

    @Transactional
    public Object insertWorksReport(WorksDto worksDto) {
        System.out.println("worksDto = " + worksDto);
        int result = worksMapper.insertWorksReport(worksDto);
        System.out.println("result = " + result);
        return (result > 0)? "등록성공":"등록실패";
    }

    @Transactional
    public Object updateWorksReportForAdmin(WorksDto worksDto) {
        int result = worksMapper.updateWorksReportForAdmin(worksDto);
        return (result > 0)? "등록성공":"등록실패";
    }

    @Transactional
    public Object updateWorksReportForEmp(WorksDto worksDto) {
        int result = worksMapper.updateWorksReportForEmp(worksDto);
        return (result > 0)? "등록성공":"등록실패";
    }


    @Transactional
    public Object deleteWorksReportForAdmin(Integer worksReportCode) {
        int result = worksMapper.deleteWorksReportForAdmin(worksReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

    @Transactional
    public Object deleteWorksReportForEmp(Integer worksReportCode) {
        int result = worksMapper.deleteWorksReportForEmp(worksReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }


}
