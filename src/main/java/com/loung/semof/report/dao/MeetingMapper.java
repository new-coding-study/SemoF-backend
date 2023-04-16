package com.loung.semof.report.dao;

import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.report.dto.MeetingDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일이름 : MeetingMapper.java
 * @프로젝트 : Semof
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이지형
 * @클래스설명 : 회의보고서 crud를 위한 mapper.
 */
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
