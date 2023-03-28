package com.loung.semof.attendance.dao;

import com.loung.semof.attendance.dto.AttendanceDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttendanceMapper {

    /* 사원 근태정보 상세 조회 */
    AttendanceDto selectAttendanceDetail(int empNo);

    /* 사원 근태기록 조회 */
    List<AttendanceDto> selectAttendanceList(int empNo);


    /* 총 갯수 구하기 */
    // int selectAttendanceTotal();

    /* 페이징 처리한 스티커 전체 조회 */
    // List<ReadAttendanceDto> selectAttendanceListWithPaging(SelectCriteria selectCriteria);

    /* 스티커 상세 조회 */
    // ReadAttendanceDto selectAttendance(String attendanceCode);

    /* 카테고리별 전제 조회 */
    // List<ReadAttendanceDto> selectAttendanceListAboutCategory(String categoryCode);

    /* 타입별 전제 조회 */
    // List<ReadAttendanceDto> selectAttendanceListAboutType(String typeCode);

    /* 스티커 추가 (관리자) */
    // int insertAttendance(AttendanceDto product);

    /* 스티커 수정 (관리자) */
    // int updateAttendance(AttendanceDto product);

    /* 스티커 삭제 (관리자) */
    // int deleteAttendance(String attendanceNo);

    /* 스티커 검색 */
    // List<ReadAttendanceDto> attendanceListWithSearchValue(HashMap<String, String> search);
}
