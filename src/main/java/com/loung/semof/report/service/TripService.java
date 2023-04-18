package com.loung.semof.report.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dao.TripMapper;
import com.loung.semof.report.dto.TripDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @파일이름 : TripService.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-05
 * @작성자 : 이지형
 * @클래스설명 : 출장보고서 crud를 위한 service.
 */
@Service
public class TripService {

    private final TripMapper tripMapper;

    public TripService(TripMapper tripMapper) {
        this.tripMapper = tripMapper;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 관리자를 위한 보고서 전체 갯수를 조회.
     */
    public int selectTripReportTotalForAdmin() {
        int result = tripMapper.selectTripReportTotalForAdmin();
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllTripReportForAdminWithPaging(SelectCriteria selectCriteria){
        List<TripDto> tripDto = tripMapper.selectAllTripReportForAdminWithPaging(selectCriteria);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽지 않은 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllTripNStatusForAdmin(SelectCriteria selectCriteria) {
        List<TripDto> tripDto = tripMapper.selectAllTripNStatusForAdmin(selectCriteria);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽은 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllTripYStatusForAdmin(SelectCriteria selectCriteria) {
        List<TripDto> tripDto = tripMapper.selectAllTripYStatusForAdmin(selectCriteria);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 직원를 위한 보고서 전체 갯수를 조회.
     */
    public int selectTripReportTotalForEmp(int empNo){
        int result = tripMapper.selectTripReportTotalForEmp(empNo);
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllTripReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo) {
        List<TripDto> tripDto = tripMapper.selectAllTripReportForEmpWithPaging(selectCriteria, empNo);
        for (int i =0; i <tripDto.size(); i++);
        return tripDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽지 않은 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllTripNStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<TripDto> tripDto = tripMapper.selectAllTripNStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽은 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllTripYStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<TripDto> tripDto = tripMapper.selectAllTripYStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<tripDto.size(); i++);
        return tripDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 상세 조회(관리자).
     */
    public Object detailTripReportForAdmin(Integer tripReportCode) {
        TripDto tripDto = tripMapper.detailTripReportForAdmin(tripReportCode);
        return tripDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 상세 조회(직원).
     */
    public Object detailTripReportForEmp(Integer tripReportCode) {
        TripDto tripDto = tripMapper.detailTripReportForEmp(tripReportCode);
        return tripDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 등록을 위한 메소드.
     */
    @Transactional
    public Object insertTripReport(TripDto tripDto) {
        int result = tripMapper.insertTripReport(tripDto);
        return (result > 0)? "등록성공":"등록실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서에 comment를 작성하기 메소드(관리자).
     */
    @Transactional
    public Object updateTripReportForAdmin(TripDto tripDto) {
        int result = tripMapper.updateTripReportForAdmin(tripDto);
        return (result>0)? "의견작성 성공":"작성실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서에 내용을 수정하기 위한 메소드.
     */
    @Transactional
    public Object updateTripReportForEmp(TripDto tripDto) {
        int result = tripMapper.updateTripReportForEmp(tripDto);
        return (result>0)? "수정성공":"수정실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서를 삭제하기 위한 메소드(관리자).
     */
    @Transactional
    public Object deleteTripReportForAdmin(Integer tripReportCode) {
        int result = tripMapper.deleteTripReportForAdmin(tripReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서를 삭제하기 위한 메소드(직원).
     */
    @Transactional
    public Object deleteTripReportForEmp(Integer tripReportCode) {
        int result = tripMapper.deleteTripReportForEmp(tripReportCode);
        return (result>0)? "보고서 삭제 성공":"삭제실패";
    }

}
