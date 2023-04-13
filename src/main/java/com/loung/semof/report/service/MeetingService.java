package com.loung.semof.report.service;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dao.MeetingMapper;
import com.loung.semof.report.dto.MeetingDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeetingService {

    private final MeetingMapper meetingMapper;

    public MeetingService(MeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    public int selectMeetingReportTotalForAdmin() {
        int result = meetingMapper.selectMeetingReportTotalForAdmin();
        return result;
    }

    public Object selectAllMeetingReportForAdminWithPaging(SelectCriteria selectCriteria){
        List<MeetingDto> MeetingDto = meetingMapper.selectAllMeetingReportForAdminWithPaging(selectCriteria);
        for(int i = 0; i<MeetingDto.size(); i++);
        return MeetingDto;
    }

    public int selectMeetingReportTotalForEmp() {
        int result = meetingMapper.selectMeetingReportTotalForEmp();
        return result;
    }

    public Object selectAllMeetingReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo){
        List<MeetingDto> MeetingDto = meetingMapper.selectAllMeetingReportForEmpWithPaging(selectCriteria, empNo);
        for(int i = 0; i<MeetingDto.size(); i++);
        return MeetingDto;
    }


    public Object selectDetailMeetingForAdmin(Integer meetingReportCode) {
        MeetingDto meetingDto = meetingMapper.selectDetailMeetingForAdmin(meetingReportCode);
        return meetingDto;
    }

    public Object selectDetailMeetingForEmp(Integer meetingReportCode) {
        MeetingDto meetingDto = meetingMapper.selectDetailMeetingForEmp(meetingReportCode);
        return meetingDto;
    }

    @Transactional
    public Object insertMeetingReport(MeetingDto meetingDto) {
        int result = meetingMapper.insertMeetingReport(meetingDto);
        return (result > 0)? "등록성공":"등록실패";
    }
    @Transactional
    public Object updateMeetingReportForAdmin(MeetingDto meetingDto) {
        int result = meetingMapper.updateMeetingReportForAdmin(meetingDto);
        return (result > 0)? "수정성공":"수정실패";
    }
    @Transactional
    public Object updateMeetingReportForEmp(MeetingDto meetingDto) {
        int result = meetingMapper.updateMeetingReportForEmp(meetingDto);
        return (result > 0)? "수정성공":"수정실패";
    }

    @Transactional
    public Object deleteMeetingReportForAdmin(Integer meetingReportCode) {
        int result = meetingMapper.deleteMeetingReportForAdmin(meetingReportCode);
        return (result > 0)? "삭제성공":"삭제실패";
    }

    @Transactional
    public Object deleteMeetingReportForEmp(Integer meetingReportCode) {
        int result = meetingMapper.deleteMeetingReportForEmp(meetingReportCode);
        return (result > 0)? "삭제성공":"삭제실패";
    }

    public Object selectAllMeetingNStatusForAdmin(SelectCriteria selectCriteria) {
        List<MeetingDto> meetingDto = meetingMapper.selectAllMeetingNStatusForAdmin(selectCriteria);
        for(int i = 0; i<meetingDto.size(); i++);
        return meetingDto;
    }
    public Object selectAllMeetingYStatusForAdmin(SelectCriteria selectCriteria) {
        List<MeetingDto> meetingDto = meetingMapper.selectAllMeetingYStatusForAdmin(selectCriteria);
        for(int i = 0; i<meetingDto.size(); i++);
        return meetingDto;
    }

    public Object selectAllMeetingNStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<MeetingDto> meetingDto = meetingMapper.selectAllMeetingNStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<meetingDto.size(); i++);
        return meetingDto;
    }
    public Object selectAllMeetingYStatusForEmp(SelectCriteria selectCriteria, int empNo) {
        List<MeetingDto> meetingDto = meetingMapper.selectAllMeetingYStatusForEmp(selectCriteria, empNo);
        for(int i = 0; i<meetingDto.size(); i++);
        return meetingDto;
    }
}
