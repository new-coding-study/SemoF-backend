package com.loung.semof.attendance.service;

import com.loung.semof.attendance.dao.AttendanceMapper;
import com.loung.semof.attendance.dto.AttendanceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AttendanceService {

    // @Value("${image.image-dir}")
    // private String IMAGE_DIR;
    // @Value("${image.image-url}")
    // private String IMAGE_URL;

    private final AttendanceMapper attendanceMapper;

    public AttendanceService(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }

    /**
     * @작성일 : 2023-03-27 027
     * @작성자 : sik
     * @메소드설명 : 사원 근태정보 상세 조회
     */
    public AttendanceDto selectAttendanceDetail(int empNo) throws Exception{
        log.info("[AttendanceService] selectAttendanceDetail Start ===================================");
        AttendanceDto attendanceDto = attendanceMapper.selectAttendanceDetail(empNo);
        log.info("[AttendanceService] selectAttendanceDetail End ===================================");
        if (attendanceDto == null) {
            throw new Exception("근태 정보 조회 실패");
        }
        return attendanceDto;
    }

    public List<AttendanceDto> selectAttendanceList(int empNo) throws Exception {
        log.info("[AttendanceService] selectAttendanceList Start ===================================");
        List<AttendanceDto> attendanceDtoList = attendanceMapper.selectAttendanceList(empNo);
        log.info("[AttendanceService] selectAttendanceList End ===================================");
        if (attendanceDtoList == null) {
            throw new Exception("근태 기록 조회 실패");
        }
        return attendanceDtoList;
    }

    public AttendanceDto selectVacationDetail(int empNo) throws Exception{
        log.info("[AttendanceService] selectAttendanceDetail Start ===================================");
        AttendanceDto attendanceDto = attendanceMapper.selectVacationDetail(empNo);
        log.info("[AttendanceService] selectAttendanceDetail End ===================================");
        if (attendanceDto == null) {
            throw new Exception("연차 현황 조회 실패");
        }
        return attendanceDto;
    }


    public String updateAttendance(int empNo) throws Exception{
        log.info("[AttendanceService] updateAttendance Start ===================================");

        // 사원의 마지막 근무기록의 날짜 추출, 시스템 현재 날짜 조회
        AttendanceDto lastDate = attendanceMapper.selectAttendanceDetail(empNo);
        LocalDate currentDate = LocalDate.now();

        // 사원 마지막 근무 날짜와 현재 날짜가 같지 않을때만 근무기록행 추가 (날짜 같다면 당일 근무 기록이 이미 있음을 뜻함)
        if (lastDate == null || !Objects.equals(currentDate.toString(), lastDate.getAtdDate().substring(0, 10))){
            log.info("-------------날짜 비교 if문 진입-------------");
            attendanceMapper.insertAttendance(empNo);
        }

        // 사원 근무상태 기록 카운트 조회
        int countAtt = attendanceMapper.selectCountAttendanceStatusInfo(empNo);

        int statusCode;

        // 상태값과 카운트된 기록에 따라 처리 (0, 1 아니면 이미 퇴근한 상태니까 상태 변경할게 없음, 카운팅된 1이 퇴근일 케이스도 없음 0일때만 출근처리하고 그땐 출근으로 처리함)
        switch (countAtt) {
            case 0:
                log.info("-------------countAtt : 0(공석) -> 상태코드 : 1(출근)-------------");
                statusCode = 1;
                break;
            case 1:
                log.info("-------------countAtt : 1(출근) -> 상태코드 : 2(퇴근)-------------");
                statusCode = 2;
                break;
            default:
                log.info("-------------countAtt : 2(퇴근) -> 없음-------------");
                throw new Exception("에러 발생");
        }

        // 사원의 마지막 근무번호 조회(위에 생성된 기록 조회)
        int atdNo = attendanceMapper.selectLastAttendanceNo(empNo);

        // 전달할 파라미터 값 저장
        // HashMap<String, Integer> atdObject = new HashMap<>();
        // atdObject.put("atdNo", atdNo);
        // atdObject.put("statusCode", statusCode);
        // atdObject.put("empNo", empNo);

        // 파라미터 전달하여 근무상태기록
        attendanceMapper.insertAttendanceStatusInfo(atdNo, statusCode);

        // 파라미터 전달하여 근무 상태 변경
        int result = attendanceMapper.updateAttendance(atdNo, empNo, statusCode);
        log.info("[AttendanceService] updateAttendance End ===================================");
        log.info("[AttendanceService] result > 0 성공: "+ result);
        if (result > 0) {
            return "상태 변경 성공";
        } else {
            throw new Exception("상태 변경 실패");
        }
    }


    // 아래 if문을 swith로 간결하게 변환 (전달받은 상태값에 따라 조건 달라지고 그 안에서 카운팅된 갯수로 출퇴근 상태 및 에러 처리)
        /* switch (statusCode) {
            case 1:
                log.info("-------------상태코드 : 1(출근상태)-------------");
                if (countAtt == 0 || countAtt == 2){
                    log.info("-------------에러 발생-------------");
                    throw new Exception("퇴근 실패)");
                }
                statusCode = 2;
                break;
            case 2:
                log.info("-------------상태코드 : 2(퇴근상태)-------------");
                if (countAtt == 0 || countAtt == 1 || countAtt == 2){
                    log.info("-------------에러 발생-------------");
                    throw new Exception("에러 발생");
                }
                break;
            default:
                log.info("-------------상태코드 : 3(공석상태)-------------");
                if (countAtt == 1 || countAtt == 2){
                    log.info("-------------에러 발생-------------");
                    throw new Exception("출근 실패");
                }
                break;
        } */
        /* if (statusCode == 1) {
            log.info("-------------상태코드 : 1(출근상태)-------------");
            if (countAtt == 0 || countAtt == 2){
                log.info("-------------에러 발생-------------");
                throw new Exception("퇴근 실패)");
            }
        } else if (statusCode == 2) {
            log.info("-------------상태코드 : 2(퇴근상태)-------------");
            if (countAtt == 0 || countAtt == 1 || countAtt == 2){
                log.info("-------------에러 발생-------------");
                throw new Exception("에러 발생");
            }
        } else {
            log.info("-------------상태코드 : 3(공석상태)-------------");
            if (countAtt == 1 || countAtt == 2){
                log.info("-------------에러 발생-------------");
                throw new Exception("출근 실패");
            }
        } */





    /* 총 갯수 구하기 */
    /* public int selectAttendanceTotal() {
        log.info("[AttendanceService] selectAttendanceTotal Start ===================================");
        int result = attendanceMapper.selectAttendanceTotal();

        log.info("[AttendanceService] selectAttendanceTotal End ===================================");
        return result;
    } */

    /* 페이징 처리 된 전체 조회 */
    /* public Object selectAttendanceListWithPaging(SelectCriteria selectCriteria) {
        log.info("[AttendanceService] selectAttendanceListWithPaging Start ===================================");
        List<ReadAttendanceDto> attendanceList = attendanceMapper.selectAttendanceListWithPaging(selectCriteria);

        for(int i = 0 ; i < attendanceList.size() ; i++) {
            attendanceList.get(i).setAttendanceImageUrl(IMAGE_URL + attendanceList.get(i).getAttendanceImageUrl());
        }
        log.info("[AttendanceService] selectAttendanceListWithPaging End ===================================");
        return attendanceList;
    } */

    /* 스티커 상세 조회 */
    /* public ReadAttendanceDto selectAttendance(String attendanceCode) {
        log.info("[AttendanceService] selectAttendance Start ===================================");
        ReadAttendanceDto attendanceDto = attendanceMapper.selectAttendance(attendanceCode);
        attendanceDto.setAttendanceImageUrl(IMAGE_URL + attendanceDto.getAttendanceImageUrl());
        log.info("[AttendanceService] selectAttendance End ===================================");
        return attendanceDto;
    } */

    /* 카테고리별 전체 조회 */
    /* public List<ReadAttendanceDto> selectAttendanceListAboutCategory(String categoryCode) {
        log.info("[AttendanceService] selectAttendanceListAboutCategory Start ===================================");

        List<ReadAttendanceDto> selectAttendanceListAboutCategory = attendanceMapper.selectAttendanceListAboutCategory(categoryCode);

        for(int i = 0 ; i < selectAttendanceListAboutCategory.size() ; i++) {
            selectAttendanceListAboutCategory.get(i).setAttendanceImageUrl(IMAGE_URL + selectAttendanceListAboutCategory.get(i).getAttendanceImageUrl());
        }

        log.info("[AttendanceService] selectAttendanceListAboutCategory End ==============================");

        return selectAttendanceListAboutCategory;
    } */

    /* 타입별 전체 조회 */
    /* public List<ReadAttendanceDto> selectAttendanceListAboutType(String typeCode) {
        log.info("[AttendanceService] selectAttendanceListAboutType Start ===================================");

        List<ReadAttendanceDto> selectAttendanceListAboutType = attendanceMapper.selectAttendanceListAboutType(typeCode);

        for(int i = 0 ; i < selectAttendanceListAboutType.size() ; i++) {
            selectAttendanceListAboutType.get(i).setAttendanceImageUrl(IMAGE_URL + selectAttendanceListAboutType.get(i).getAttendanceImageUrl());
        }

        log.info("[AttendanceService] selectAttendanceListAboutType End ==============================");

        return selectAttendanceListAboutType;
    } */

    /* 스티커 추가 */
    /* @Transactional
    public String insertAttendance(AttendanceDto attendanceDto) {
        log.info("[AttendanceService] insertAttendance Start ===================================");
        log.info("[AttendanceService] attendanceDto : " + attendanceDto);
        String imageName = UUID.randomUUID().toString().replace("-", "");
        String replaceFileName = null;
        int result;
        log.info("[AttendanceService] IMAGE_DIR : " + IMAGE_DIR);
        log.info("[AttendanceService] imageName : " + imageName);
        try {
            replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, attendanceDto.getAttendanceImage());
            log.info("[AttendanceService] replaceFileName : " + replaceFileName);

            attendanceDto.setAttendanceImageUrl(replaceFileName);

            log.info("[AttendanceService] insert Image Name : "+ replaceFileName);

            result = attendanceMapper.insertAttendance(attendanceDto);
        } catch (IOException e) {
            log.info("[AttendanceService] IOException IMAGE_DIR : "+ IMAGE_DIR);

            log.info("[AttendanceService] IOException deleteFile : "+ replaceFileName);

            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }
        log.info("[AttendanceService] result > 0 성공: "+ result);
        return (result > 0) ? "스티커 등록 성공" : "스티커 등록 실패";
    } */

    /* 스티커 수정 */
    /* @Transactional
    public Object updateAttendance(AttendanceDto attendanceDto) {
        log.info("[AttendanceService] updateAttendance Start ===================================");
        log.info("[AttendanceService] attendanceDto : " + attendanceDto);
        String replaceFileName = null;
        int result;
        try {
            String oriImage = attendanceMapper.selectAttendance(String.valueOf(attendanceDto.getAttendanceCode())).getAttendanceImageUrl();
            log.info("[updateAttendance] oriImage : " + oriImage);

            if(attendanceDto.getAttendanceImage() != null){
                // 이미지 변경 진행
                String imageName = UUID.randomUUID().toString().replace("-", "");
                replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, attendanceDto.getAttendanceImage());

                log.info("[updateAttendance] IMAGE_DIR!!"+ IMAGE_DIR);
                log.info("[updateAttendance] imageName!!"+ imageName);

                log.info("[updateAttendance] InsertFileName : " + replaceFileName);
                attendanceDto.setAttendanceImageUrl(replaceFileName);

                log.info("[updateAttendance] deleteImage : " + oriImage);
                boolean isDelete = FileUploadUtils.deleteFile(IMAGE_DIR, oriImage);
                log.info("[update] isDelete : " + isDelete);
            } else {
                // 이미지 변경 없을 시
                attendanceDto.setAttendanceImageUrl(oriImage);
            }
            result = attendanceMapper.updateAttendance(attendanceDto);
        } catch (IOException e) {
            log.info("[updateAttendance] Exception!!");
            FileUploadUtils.deleteFile(IMAGE_DIR, replaceFileName);
            throw new RuntimeException(e);
        }
        log.info("[AttendanceService] updateAttendance End ===================================");
        log.info("[AttendanceService] result > 0 성공: "+ result);

        return (result > 0) ? "스티커 업데이트 성공" : "스티커 업데이트 실패";
    } */

    /* 스티커 삭제 */
    /* @Transactional
    public String deleteAttendance(String attendanceNo) {
        log.info("[AttendanceService] deleteAttendance Start ===================================");
        log.info("[AttendanceService] attendanceNo : " + attendanceNo);

        int result;
        result = attendanceMapper.deleteAttendance(attendanceNo);

        log.info("[AttendanceService] result > 0 성공: "+ result);
        return (result > 0) ? "스티커 삭제 성공" : "스티커 삭제 실패";
    } */

    /* 스티커 검색 조회 */
    /* public List<ReadAttendanceDto> selectSearchAttendanceList(HashMap<String, String> search) {
        log.info("[AttendanceService] selectSearchAttendanceList Start ===================================");
        log.info("[AttendanceService] searchValue : " + search);

        List<ReadAttendanceDto> attendanceListWithSearchValue = attendanceMapper.attendanceListWithSearchValue(search);
        log.info("[AttendanceService] attendanceListWithSearchValue : " + attendanceListWithSearchValue);

        for(int i = 0 ; i < attendanceListWithSearchValue.size() ; i++) {
            attendanceListWithSearchValue.get(i).setAttendanceImageUrl(IMAGE_URL + attendanceListWithSearchValue.get(i).getAttendanceImageUrl());
        }
        log.info("[AttendanceService] selectSearchAttendanceList End ===================================");

        return attendanceListWithSearchValue;
    } */

}
