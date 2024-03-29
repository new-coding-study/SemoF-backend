package com.loung.semof.approval.dao;


import com.loung.semof.approval.dto.*;
import com.loung.semof.common.dto.BranchDto;
import com.loung.semof.common.paging.SelectCriteria;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * @파일이름 : ApprovalMapper.java
 * @프로젝트 : semoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-22
 * @작성자 : 박유리
 * @클래스설명 : 설명을 여기에 작성한다.
 */
@Mapper
public interface ApprovalMapper {
    List<ApprovLineDTO> selectApprovLineListWithPaging(SelectCriteria selectCriteria);

    int insertApprovLine(ApprovLineDTO line);

    int updateApprovLine(ApprovLineDTO line);

    int deleteApprovLine(int lineNo);

    int insertApprovOrder(ApprovOrderDTO order);

    int updateApprovOrder(ApprovOrderDTO order);

//    int deleteApprovOrder(int orderNo);

    List<ApprovalDTO> selectApprovalInWithPaging(Integer empNo, SelectCriteria selectCriteria);

    ApprovalDTO selectApproval(Integer approvNo);

    int insertApproval(ApprovalDTO approval);

    int updateApproval(ApprovalDTO approval);

    int deleteApproval(int ApprovNo);

    int insertApprovFile(ApprovFileDTO file);

    int updateApprovFile(ApprovFileDTO file);

//    int deleteFile(int fileNo);

//    ApprovContentDTO selectApprovContent(ApprovContentDTO content);

    int insertApprovContent(ApprovContentDTO content);

    int updateContent(ApprovContentDTO content);

//    int deleteContent(int contentNo);

//    ApprovOpinDTO selectOpinion(int opinNo);

    int insertOpinion(ApprovOpinDTO opinion);

    int updateStatus(String status, Integer approvNo, int rowNum);

    int selectApprovalTotal(Integer empNo);
    int selectApprovOutTotal(Integer empNo);
    int selectApprovLineTotal();

    String selectLatestStatus(Integer approvNo);

    String selectCategory(Integer approvNo);

    List<String> selectFormTitle();

    List<BranchDto> selectBranch();

    List<String> selectDepartment();
    List<String> selectJobNEmpName();

    List<ApprovLineDTO> selectLineList();

    ApprovLineDTO selectLineDetail(Integer lineNo);

    List<ApprovalDTO> selectApprovalOutWithPaging(Integer empNo, SelectCriteria selectCriteria);

    List<ApprovStatusDTO> selectStatus(Integer approvNo);

//    int insertApprovOrders(List<ApprovOrderDTO> orders);

//    int insertLine(ApprovLineDTO line);

    List<ApprovOpinDTO> selectOpinion(Integer approvNo);

    Integer selectStatusOrder(Integer lineNo, Long empNo);

    List<ApprovFileDTO> selectFiles(Integer approvNo);

    List<ApprovalDTO> selectFinApprovalInWithPaging(Integer empNo, SelectCriteria selectCriteria);

    int selectFinApprovalTotal(Integer empNo);

    List<ApprovalDTO> selectFinApprovalOutWithPaging(Integer empNo, SelectCriteria selectCriteria);

    int selectFinApprovOutTotal(Integer empNo);
}
