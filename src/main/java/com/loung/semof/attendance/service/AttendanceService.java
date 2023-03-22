package com.loung.semof.attendance.service;

import com.loung.semof.attendance.dao.AttendanceMapper;
import com.loung.semof.attendance.dto.AttendanceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    /* 스티커 상세 조회 */
    public AttendanceDto selectAttendanceDetail(int empNo) {
        log.info("[AttendanceService] selectAttendance Start ===================================");
        AttendanceDto attendanceDto = attendanceMapper.selectAttendanceDetail(empNo);
        // attendanceDto.setAttendanceImageUrl(IMAGE_URL + attendanceDto.getAttendanceImageUrl());
        log.info("[AttendanceService] selectAttendance End ===================================");
        return attendanceDto;
    }

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
