package com.loung.semof.attendance.dao;

import com.loung.semof.attendance.dto.AttendanceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AttendanceMapper {

    /* 총 갯수 구하기 */
    int selectAttendanceTotal(int empNo);

    /* 페이징 처리한 스티커 전체 조회 SelectCriteria selectCriteria */
    List<AttendanceDto> selectAttendanceListWithPaging(@Param("endRow") int endRow, @Param("startRow") int startRow, @Param("empNo") int empNo);

    /* 사원 근태정보 상세 조회 */
    AttendanceDto selectAttendanceDetail(int empNo);

    /* 사원 근태기록 조회 */
    List<AttendanceDto> selectAttendanceList(int empNo);

    /* 아래부터 근태 상태 변경 */
    /* 해당 사원의 마지막 근무번호 불러오기 */
    int selectLastAttendanceNo(int empNo);

    /* 사원 근무상태정보 기록 카운팅 (갯수를 기준으로 출퇴근 처리 제한) */
    int selectCountAttendanceStatusInfo(int empNo);

    /* 사원 근무정보 행 생성 */
    int insertAttendance(int empNo);

    /* 사원 근무상태정보 기록 생성(출퇴근 발생시간) */
    // int insertAttendanceStatusInfo(@Param("atdNo") int atdNo, @Param("statusCode") int statusCode);
    int insertAttendanceStatusInfo(HashMap<String, String> data);

    /* 사원 근무정보 출퇴근 시간 변경 */
    int updateAttendance(@Param("atdNo") int atdNo, @Param("empNo") int empNo, @Param("statusCode") int statusCode);
    // int updateAttendance(HashMap<String, Integer> atdObject);
}
