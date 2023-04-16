package com.loung.semof.report.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.TripDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일이름 : TripMapper.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이지형
 * @클래스설명 : 출장보고서 crud를 위한 mapper.
 */
@Mapper
public interface TripMapper {

    int selectTripReportTotalForAdmin();
    int selectTripReportTotalForEmp();
    List<TripDto> selectAllTripReportForAdminWithPaging(SelectCriteria selectCriteria);
    List<TripDto> selectAllTripReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo);
    TripDto detailTripReportForAdmin(Integer tripReportCode);
    TripDto detailTripReportForEmp(Integer tripReportCode);
    int insertTripReport(TripDto tripDto);
    int updateTripReportForAdmin(TripDto tripDto);
    int updateTripReportForEmp(TripDto tripDto);
    int deleteTripReportForAdmin(Integer tripReportCode);
    int deleteTripReportForEmp(Integer tripReportCode);

    List<TripDto> selectAllTripNStatusForAdmin(SelectCriteria selectCriteria);

    List<TripDto> selectAllTripYStatusForAdmin(SelectCriteria selectCriteria);

    List<TripDto> selectAllTripNStatusForEmp(SelectCriteria selectCriteria, int empNo);

    List<TripDto> selectAllTripYStatusForEmp(SelectCriteria selectCriteria, int empNo);
}
