package com.loung.semof.report.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.MeetingDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMapper {

    int selectMeetingReportTotalForAdmin();
    int selectMeetingReportTotalForEmp();
    List<MeetingDto> selectAllMeetingReportForAdminWithPaging(SelectCriteria selectCriteria);
    List<MeetingDto> selectAllMeetingReportForEmpWithPaging(SelectCriteria selectCriteria, int empNo);
    MeetingDto selectDetailMeetingForAdmin(Integer meetingReportCode);
    MeetingDto selectDetailMeetingForEmp(Integer meetingReportCode);
    int insertMeetingReport(MeetingDto meetingDto);
    int updateMeetingReportForAdmin(MeetingDto meetingDto);
    int updateMeetingReportForEmp(MeetingDto meetingDto);
    int deleteMeetingReportForAdmin(Integer meetingReportCode);
    int deleteMeetingReportForEmp(Integer meetingReportCode);

    List<MeetingDto> selectAllMeetingNStatusForAdmin(SelectCriteria selectCriteria);

    List<MeetingDto> selectAllMeetingYStatusForAdmin(SelectCriteria selectCriteria);

    List<MeetingDto> selectAllMeetingNStatusForEmp(SelectCriteria selectCriteria, int empNo);

    List<MeetingDto> selectAllMeetingYStatusForEmp(SelectCriteria selectCriteria, int empNo);
}
