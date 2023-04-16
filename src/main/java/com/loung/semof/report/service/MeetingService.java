package com.loung.semof.report.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dao.MeetingMapper;
import com.loung.semof.report.dto.MeetingDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @파일이름 : MeetingService.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-05
 * @작성자 : 이지형
 * @클래스설명 : 회의보고서 crud를 위한 service.
 */
@Service
public class MeetingService {

    private final MeetingMapper meetingMapper;

    public MeetingService(MeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 관리자를 위한 보고서 전체 갯수를 조회.
     */
    public int selectMeetingReportTotalForAdmin() {
        int result = meetingMapper.selectMeetingReportTotalForAdmin();
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllMeetingReportForAdminWithPaging(SelectCriteria selectCriteria){
        List<MeetingDto> MeetingDto = meetingMapper.selectAllMeetingReportForAdminWithPaging(selectCriteria);
        for(int i = 0; i<MeetingDto.size(); i++);
        return MeetingDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽지 않은 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllMeetingNStatusForAdmin(SelectCriteria selectCriteria) {
        List<MeetingDto> meetingDto = meetingMapper.selectAllMeetingNStatusForAdmin(selectCriteria);
        for(int i = 0; i<meetingDto.size(); i++);
        return meetingDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽은 보고서 전체 조회 및 페이징(관리자).
     */
    public Object selectAllMeetingYStatusForAdmin(SelectCriteria selectCriteria) {
        List<MeetingDto> meetingDto = meetingMapper.selectAllMeetingYStatusForAdmin(selectCriteria);
        for(int i = 0; i<meetingDto.size(); i++);
        return meetingDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 직원를 위한 보고서 전체 갯수를 조회.
     */
    public int selectMeetingReportTotalForEmp(int empNo) {
        int result = meetingMapper.selectMeetingReportTotalForEmp(empNo);
        return result;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllMeetingReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo){
        List<MeetingDto> MeetingDto = meetingMapper.selectAllMeetingReportForEmpWithPaging(selectCriteria, empNo);
        for(int i = 0; i<MeetingDto.size(); i++);
        return MeetingDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽지 않은 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllMeetingNStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<MeetingDto> meetingDto = meetingMapper.selectAllMeetingNStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<meetingDto.size(); i++);
        return meetingDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 읽은 보고서 전체 조회 및 페이징(직원).
     */
    public Object selectAllMeetingYStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<MeetingDto> meetingDto = meetingMapper.selectAllMeetingYStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<meetingDto.size(); i++);
        return meetingDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 상세 조회(관리자).
     */
    public Object selectDetailMeetingForAdmin(Integer meetingReportCode) {
        MeetingDto meetingDto = meetingMapper.selectDetailMeetingForAdmin(meetingReportCode);
        return meetingDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 상세 조회(직원).
     */
    public Object selectDetailMeetingForEmp(Integer meetingReportCode) {
        MeetingDto meetingDto = meetingMapper.selectDetailMeetingForEmp(meetingReportCode);
        return meetingDto;
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 보고서 등록을 위한 메소드.
     */
    @Transactional
    public Object insertMeetingReport(MeetingDto meetingDto) {
        int result = meetingMapper.insertMeetingReport(meetingDto);
        return (result > 0)? "등록성공":"등록실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서에 comment를 작성하기 메소드(관리자).
     */
    @Transactional
    public Object updateMeetingReportForAdmin(MeetingDto meetingDto) {
        int result = meetingMapper.updateMeetingReportForAdmin(meetingDto);
        return (result > 0)? "의견작성성공":"작성실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서에 내용을 수정하기 위한 메소드.
     */
    @Transactional
    public Object updateMeetingReportForEmp(MeetingDto meetingDto) {
        int result = meetingMapper.updateMeetingReportForEmp(meetingDto);
        return (result > 0)? "수정성공":"수정실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서를 삭제하기 위한 메소드(관리자).
     */
    @Transactional
    public Object deleteMeetingReportForAdmin(Integer meetingReportCode) {
        int result = meetingMapper.deleteMeetingReportForAdmin(meetingReportCode);
        return (result > 0)? "삭제성공":"삭제실패";
    }

    /**
     * @작성일 : 2023.04.05
     * @작성자 : 이지형
     * @메소드설명 : 등록된 보고서를 삭제하기 위한 메소드(직원).
     */
    @Transactional
    public Object deleteMeetingReportForEmp(Integer meetingReportCode) {
        int result = meetingMapper.deleteMeetingReportForEmp(meetingReportCode);
        return (result > 0)? "삭제성공":"삭제실패";
    }

}
