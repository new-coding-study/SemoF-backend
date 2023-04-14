package com.loung.semof.report.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dao.TripMapper;
import com.loung.semof.report.dto.TripDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TripService {

    private final TripMapper tripMapper;

    public TripService(TripMapper tripMapper) {
        this.tripMapper = tripMapper;
    }

    public int selectTripReportTotalForAdmin() {
        int result = tripMapper.selectTripReportTotalForAdmin();
        return result;
    }

    public int selectTripReportTotalForEmp(){
        int result = tripMapper.selectTripReportTotalForEmp();
        return result;
    }

    public Object selectAllTripReportForAdminWithPaging(SelectCriteria selectCriteria){
        List<TripDto> tripDto = tripMapper.selectAllTripReportForAdminWithPaging(selectCriteria);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }

    public Object selectAllTripReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo) {
        List<TripDto> tripDto = tripMapper.selectAllTripReportForEmpWithPaging(selectCriteria, empNo);
        for (int i =0; i <tripDto.size(); i++);
        return tripDto;
    }

    public Object detailTripReportForAdmin(Integer tripReportCode) {
        TripDto tripDto = tripMapper.detailTripReportForAdmin(tripReportCode);
        return tripDto;
    }

    public Object detailTripReportForEmp(Integer tripReportCode) {
        TripDto tripDto = tripMapper.detailTripReportForEmp(tripReportCode);
        return tripDto;
    }

    @Transactional
    public Object insertTripReport(TripDto tripDto) {
        int result = tripMapper.insertTripReport(tripDto);
        return (result > 0)? "등록성공":"등록실패";
    }

    @Transactional
    public Object updateTripReportForAdmin(TripDto tripDto) {
        int result = tripMapper.updateTripReportForAdmin(tripDto);
        return (result>0)? "수정성공":"수정실패";
    }

    @Transactional
    public Object updateTripReportForEmp(TripDto tripDto) {
        int result = tripMapper.updateTripReportForEmp(tripDto);
        return (result>0)? "수정성공":"수정실패";
    }

    @Transactional
    public Object deleteTripReportForAdmin(Integer tripReportCode) {
        int result = tripMapper.deleteTripReportForAdmin(tripReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

    @Transactional
    public Object deleteTripReportForEmp(Integer tripReportCode) {
        int result = tripMapper.deleteTripReportForEmp(tripReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

    public Object selectAllTripNStatusForAdmin(SelectCriteria selectCriteria) {
        List<TripDto> tripDto = tripMapper.selectAllTripNStatusForAdmin(selectCriteria);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }

    public Object selectAllTripYStatusForAdmin(SelectCriteria selectCriteria) {
        List<TripDto> tripDto = tripMapper.selectAllTripYStatusForAdmin(selectCriteria);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }

    public Object selectAllTripNStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<TripDto> tripDto = tripMapper.selectAllTripNStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }

    public Object selectAllTripYStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<TripDto> tripDto = tripMapper.selectAllTripYStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }
}
