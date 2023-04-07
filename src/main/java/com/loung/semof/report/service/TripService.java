package com.loung.semof.report.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dao.TripMapper;
import com.loung.semof.report.dto.TripDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TripService {

    private final TripMapper tripReportMapper;

    public TripService(TripMapper tripReportMapper) {
        this.tripReportMapper = tripReportMapper;
    }

    public int selectTripReportTotalForAdmin() {
        int result = tripReportMapper.selectTripReportTotalForAdmin();
        return result;
    }

    public int selectTripReportTotalForEmp(){
        int result = tripReportMapper.selectTripReportTotalForEmp();
        return result;
    }

    public Object selectAllTripReportForAdminWithPaging(SelectCriteria selectCriteria){
        List<TripDto> reportDtoList = tripReportMapper.selectAllTripReportForAdminWithPaging(selectCriteria);
        for(int i = 0; i<reportDtoList.size(); i++);
        return reportDtoList;
    }

    public Object selectAllTripReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo) {
        List<TripDto> reportDtoList = tripReportMapper.selectAllTripReportForEmpWithPaging(selectCriteria, empNo);
        for (int i =0; i <reportDtoList.size(); i++);
        return reportDtoList;
    }

    public Object detailTripReportForAdmin(Integer tripReportCode) {
        TripDto tripDto = tripReportMapper.detailTripReportForAdmin(tripReportCode);
        return tripDto;
    }

    public Object detailTripReportForEmp(Integer tripReportCode) {
        TripDto tripDto = tripReportMapper.detailTripReportForEmp(tripReportCode);
        return tripDto;
    }

    @Transactional
    public Object insertTripReport(TripDto tripDto) {
        int result = tripReportMapper.insertTripReport(tripDto);
        return (result > 0)? "등록성공":"등록실패";
    }

    @Transactional
    public Object updateTripReportForAdmin(TripDto tripDto) {
        int result = tripReportMapper.updateTripReportForAdmin(tripDto);
        return (result>0)? "수정성공":"수정실패";
    }

    @Transactional
    public Object updateTripReportForEmp(TripDto tripDto) {
        int result = tripReportMapper.updateTripReportForEmp(tripDto);
        return (result>0)? "수정성공":"수정실패";
    }

    @Transactional
    public Object deleteTripReportForAdmin(Integer tripReportCode) {
        int result = tripReportMapper.deleteTripReportForAdmin(tripReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

    @Transactional
    public Object deleteTripReportForEmp(Integer tripReportCode) {
        int result = tripReportMapper.deleteTripReportForEmp(tripReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }
}
