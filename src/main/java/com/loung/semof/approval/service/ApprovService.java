package com.loung.semof.approval.service;

//import com.google.gson.Gson;
//import com.google.gson.Gson;
//import com.google.gson.Gson;
import com.loung.semof.approval.dao.ApprovalMapper;
import com.loung.semof.approval.dto.*;
import com.loung.semof.common.dto.BranchDto;
import com.loung.semof.common.paging.SelectCriteria;
import com.loung.semof.util.FileUploadUtils;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
        import java.util.List;
import java.util.UUID;

/**
 * @파일이름 : ApprovService.java
 * @프로젝트 : semoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-23
 * @작성자 : 박유리
 * @클래스설명 : 설명을 여기에 작성한다.
 */
@Service
public class ApprovService {

    @Value("${file.file-dir}")
    private String FILE_DIR;

    @Value("${file.file-url}")
    private String FILE_URL;

    private final ApprovalMapper approvMapper;

    public ApprovService(ApprovalMapper approvalMapper) {
        this.approvMapper = approvalMapper;
    }

    public Object insertApprovLine(ApprovLineDTO line) {
        System.out.println("line = " + line);
//        System.out.println("orders = " + orders);
//        line.setApprovOrderDTOList(orders);
        for(int i=0;i<line.getApprovOrderDTOList().size();i++){
            System.out.println(line.getApprovOrderDTOList().get(i));

        }
//        Gson gson = new Gson();
//        String jsonString = gson.toJson(line);
//        System.out.println("jsonString = " + jsonString);
        int result = 0;
        int lineResult = approvMapper.insertApprovLine(line);
        int orderResult = 0;
        for(int i =0; i<line.getApprovOrderDTOList().size();i++){
            orderResult += approvMapper.insertApprovOrder(line.getApprovOrderDTOList().get(i));
        }
        if(lineResult>0 && orderResult == line.getApprovOrderDTOList().size()){
            result = 1;
        }
        return(result > 0 ) ? "결재라인등록성공" : "결재라인등록실패";
    }
/**
 * @작성일 : 2023-03-23
 * @작성자 : 박유리
 * @메소드설명 : 설명을 여기에 작성한다.
 */

    public Object insertOpinion(ApprovOpinDTO opinion) {
        int result =0;

        result = approvMapper.insertOpinion(opinion);

        return(result > 0 ) ? "의견등록성공" : "의견등록실패";
    }

    public String insertApproval(ApprovalDTO approval, List<MultipartFile> files) {

//        approval.setCategory(category);

        List<ApprovFileDTO> fileDTOs = new ArrayList<>();
        System.out.println("결재등록 서비스 호출");
        System.out.println("files = " + files);
        for(int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            ApprovFileDTO fileDTO = new ApprovFileDTO();

            fileDTO.setOriginName(file.getOriginalFilename());

            String fileName = UUID.randomUUID().toString().replace("-","");
            fileDTO.setNewName(fileName);
            try {
                String savedFileName = FileUploadUtils.saveFile(FILE_DIR, fileName, file);
                fileDTO.setFilePath(savedFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileDTOs.add(fileDTO);
        }


        int approvResult = approvMapper.insertApproval(approval);
        int fileResult = 0;
        int contentResult = 0;
        for(int i = 0; i < fileDTOs.size(); i++){
            fileResult += approvMapper.insertApprovFile(fileDTOs.get(i));
        }

        for(int i = 0; i < approval.getApprovContentDTOList().size(); i++){
            contentResult += approvMapper.insertApprovContent(approval.getApprovContentDTOList().get(i));
        }

        if (approvResult > 0 && fileResult == files.size() && contentResult == approval.getApprovContentDTOList().size()) {
            return "결재상신성공";
        } else {
            return "결재상신실패";
        }
    }
//    public Object insertApproval(ApprovalDTO approval, List<MultipartFile> file) {
//        List<ApprovFileDTO> files = new ArrayList<>();
//
//        String otherFileName = null;
//        for(int i =0; i<file.size();i++){
//            ApprovFileDTO fileDTO = new ApprovFileDTO();
//
//            fileDTO.setOriginName(file.get(i).getOriginalFilename());
//
//            String fileName = UUID.randomUUID().toString().replace("-","");
//            fileDTO.setNewName(fileName);
//            try {
//                otherFileName = FileUploadUtils.saveFile(FILE_DIR, fileName,fileDTO.getApprovFile());
//                fileDTO.setFilePath(otherFileName);
//            } catch (IOException e) {
//                FileUploadUtils.deleteFile(FILE_DIR, otherFileName);
//                throw new RuntimeException(e);
//            }
//            files.add(fileDTO);
//        }
////        approval.setApprovFileDTOList(files);
////        approval.setApprovContentDTOList(contents);
//        int result = 0;
//        int approvResult = approvMapper.insertApproval(approval);
//        int fileResult = 0;
//        int contentResult = 0;
//        for(int i =0; i<files.size();i++){
//            fileResult += approvMapper.insertApprovFile(files.get(i));
//        }
//
//        for(int i =0; i<approval.getApprovContentDTOList().size();i++){
//            contentResult += approvMapper.insertApprovContent(approval.getApprovContentDTOList().get(i));
//        }
//        if(approvResult>0 && fileResult == file.size() && contentResult == approval.getApprovContentDTOList().size()){
//            result = 1;
//        }
//
//        return(result>0)? "결재상신성공" : "결재상신실패";
//    }

    public Object selectApprovalInWithPaging(SelectCriteria selectCriteria) {
        List<ApprovalDTO> approvalList = approvMapper.selectApprovalInWithPaging(selectCriteria);
        for(int i=0 ; i<approvalList.size();i++){
            approvalList.get(i).setCategory(approvMapper.selectCategory(approvalList.get(i).getApprovNo()));
            approvalList.get(i).setStatus(approvMapper.selectLatestStatus(approvalList.get(i).getApprovNo()));
            System.out.println(approvalList.get(i).getCategory());
            System.out.println(approvalList.get(i).getStatus());
        }
//        String[] status = approvMapper.selectLatestStatus();
//        String status = approvMapper.selectLatestStatus();
//        for(int i=0; i<approvalList.size(); i++){
//            approvalList.get(i).getApprovFileDTOList().get(i).setFilePath(FILE_DIR + approvalList.get(i).getApprovFileDTOList().get(i).getFilePath());
//        }
        return approvalList;
    }

    public int selectApprovalTotal() {
        int result = approvMapper.selectApprovalTotal();

        return result;
    }


    public int selectApprovalLineTotal() {
        int result = approvMapper.selectApprovLineTotal();

        return result;
    }

    public Object selectApprovLineListWithPaging(SelectCriteria selectCriteria) {
        List<ApprovLineDTO> approvLineList = approvMapper.selectApprovLineListWithPaging(selectCriteria);

//        ArrayList list = new ArrayList();

//        for(int i=0;i<approvLineList.size();i++){
//            list = (ArrayList) approvLineList.get(i).getApprovOrderDTOList();
//            approvLineList.get(i).setApprovOrderDTOList(list);
//            System.out.println("approvLineList.get(i).getApprovOrderDTOList() = " + approvLineList.get(i).getApprovOrderDTOList());
//        }

//        Gson gson = new Gson();
//        String jsonString = gson.toJson(approvLineList);
//        System.out.println("jsonString = " + jsonString);

        System.out.println("approvLineList = " + approvLineList);

        return approvLineList;
    }

    public Object selectApproval(Integer approvNo) {
        ApprovalDTO approvalDTO = approvMapper.selectApproval(approvNo);
        approvalDTO.setCategory(approvMapper.selectCategory(approvNo));
        approvalDTO.setStatus(approvMapper.selectLatestStatus(approvNo));
        System.out.println("approvalDTO = " + approvalDTO);


        for(int i =0 ; i<approvMapper.selectStatus(approvNo).size();i++){
            System.out.println("approvMapper.selectStatus(approvNo).get(i)) = " + approvMapper.selectStatus(approvNo).get(i));

        }
        System.out.println("매퍼의 값 : "+approvMapper.selectStatus(approvNo));
            System.out.println("approvalDTO.getStatuses() = " + approvalDTO.getStatuses());
//        }
        return approvalDTO;
    }

//    public Object updateApproval(ApprovalDTO approval, List<MultipartFile> file, List<ApprovContentDTO> contents) {
//        List<ApprovFileDTO> files = new ArrayList<>();
//
//        String otherFileName = null;
//
//        for(int i =0; i<file.size();i++){
//            try {
//            String originPath = approvMapper.selectApproval(approval.getApprovNo()).getApprovFileDTOList().get(i).getFilePath();
//
//            String originName = approvMapper.selectApproval(approval.getApprovNo()).getApprovFileDTOList().get(i).getOriginName();
//
//            String originNewName = approvMapper.selectApproval(approval.getApprovNo()).getApprovFileDTOList().get(i).getNewName();
//
//            MultipartFile originFile = approvMapper.selectApproval(approval.getApprovNo()).getApprovFileDTOList().get(i).getApprovFile();
//
//            Integer originNo = approvMapper.selectApproval(approval.getApprovNo()).getApprovFileDTOList().get(i).getFileNo();
//
//            ApprovFileDTO fileDTO = new ApprovFileDTO();
//
//            fileDTO.setOriginName(file.get(i).getOriginalFilename());
//
//            if(approval.getApprovFileDTOList().get(i).getApprovFile() != null){
//
//                String fileName = UUID.randomUUID().toString().replace("-","");
//
//                fileDTO.setNewName(fileName);
//
//                otherFileName = FileUploadUtils.saveFile(FILE_DIR, fileName,fileDTO.getApprovFile());
//
//                fileDTO.setFilePath(otherFileName);
//
//                boolean isDelete = FileUploadUtils.deleteFile(FILE_DIR, originPath);
//
//                files.add(fileDTO);
//            }else{
//                fileDTO.setFilePath(originPath);
//                fileDTO.setApprovFile(originFile);
//                fileDTO.setFileNo(originNo);
//                fileDTO.setOriginName(originName);
//                fileDTO.setNewName(originNewName);
//                files.add(fileDTO);
//            }
//        } catch (IOException e) {
//                FileUploadUtils.deleteFile(FILE_DIR, otherFileName);
//                throw new RuntimeException(e);
//            }
//        }
//        approval.setApprovFileDTOList(files);
//        approval.setApprovContentDTOList(contents);
//        int result = 0;
//        int approvResult = approvMapper.updateApproval(approval);
//        int fileResult = 0;
//        int contentResult = 0;
//        for(int i =0; i<file.size();i++){
//            fileResult += approvMapper.updateApprovFile(approval.getApprovFileDTOList().get(i));
//        }
//        for(int i =0; i<contents.size();i++){
//            contentResult += approvMapper.updateContent(approval.getApprovContentDTOList().get(i));
//        }
//        if(approvResult>0 && fileResult == file.size() && contentResult == contents.size()){
//            result = 1;
//        }
//
//        return(result>0)? "결재서류 업데이트 성공" : "결재서류 업데이트 실패";
//    }

    public Object updateStatus(ApprovStatusDTO status) {
        int result = 0;

        result = approvMapper.updateStatus(status);

        return(result>0)? "결재처리완료":"결재처리실패";
    }

    public Object updateApprovLine(ApprovLineDTO line) {
        for(int i=0;i<line.getApprovOrderDTOList().size();i++){
            System.out.println(line.getApprovOrderDTOList().get(i));
        }
        int result = 0;
        int lineResult = approvMapper.updateApprovLine(line);
        int orderResult = 0;
        for(int i =0; i<line.getApprovOrderDTOList().size();i++){
            orderResult += approvMapper.updateApprovOrder(line.getApprovOrderDTOList().get(i));
        }
        if(lineResult>0 && orderResult ==  line.getApprovOrderDTOList().size()){
            result = 1;
        }
        return(result > 0 ) ? "결재라인업데이트성공" : "결재라인업데이트실패";
    }

//    public Object deleteApprovLine(ApprovLineDTO line) {
//        int result = 0;
//        int lineResult = approvMapper.deleteApprovLine(line.getLineNo());
//
//        int orderResult = 0;
//        List<ApprovOrderDTO> orders = line.getApprovOrderDTOList();
//
//        if(!orders.isEmpty()){
//            orderResult = approvMapper.deleteApprovOrder(line.getLineNo());
//        }
//
//        result = lineResult+orderResult;
//
//        return (result>1) ? "결재라인 삭제성공": "결재라인 삭제 실패";
//    }
    public Object deleteApprovLine(Integer lineNo) {
//        int result = 0;
        int lineResult = approvMapper.deleteApprovLine(lineNo);

//        int orderResult = 0;
//        List<ApprovOrderDTO> orders = line.getApprovOrderDTOList();
//
//        if(!orders.isEmpty()){
//            orderResult = approvMapper.deleteApprovOrder(line.getLineNo());
//        }

//        result = lineResult+orderResult;

        return (lineResult>0) ? "결재라인 삭제성공": "결재라인 삭제 실패";
    }
    public Object deleteApproval(Integer approvNo) {
//        int result = 0;
        int approvResult = approvMapper.deleteApproval(approvNo);

//        int fileResult = 0;
//        List<ApprovFileDTO> files = approval.getApprovFileDTOList();
//
//        if(!files.isEmpty()){
//            fileResult = approvMapper.deleteFile(approval.getApprovNo());
//        }
//        int contentResult = 0;
//        List<ApprovContentDTO> content = approval.getApprovContentDTOList();
//
//        if(!content.isEmpty()){
//            contentResult = approvMapper.deleteContent(approval.getApprovNo());
//        }
//        result = approvResult+fileResult+contentResult;

        return (approvResult>0) ? "결재서류 삭제성공": "결재서류 삭제 실패";
    }

    public Object selectFormTitle() {
        List<String> formTitle = new ArrayList<>();
                formTitle=approvMapper.selectFormTitle();
//        if (formTitle != null && !formTitle.isEmpty()) {
//            String formCode = formTitle.get("FORM_CODE");
//            String formTitleValue = formTitle.get("FORM_TITLE");
//            return formTitle;
//        } else {
//            System.out.println("안돼~~~~~");
            return formTitle;
//        }
    }

    public Object selectBranch() {
        List<BranchDto> branch = new ArrayList<>();
        branch = approvMapper.selectBranch();
        return branch;
    }
    public Object selectDept() {
        List<String> dept = new ArrayList<>();
        dept = approvMapper.selectDepartment();
        return dept;
    }
    public Object selectJobNEmpName() {
        List<String> jobNEmpName = new ArrayList<>();
        jobNEmpName = approvMapper.selectJobNEmpName();
        return jobNEmpName;
    }

    public List<ApprovLineDTO> selectLineList() {
        List<ApprovLineDTO> lineList = approvMapper.selectLineList();
        return lineList;
    }

    public Object selectLineDetail(Integer lineNo) {
        ApprovLineDTO lineDTO = approvMapper.selectLineDetail(lineNo);
        return lineDTO;
    }

    public Object selectApprovalOutWithPaging(SelectCriteria selectCriteria) {
        List<ApprovalDTO> approvalList = approvMapper.selectApprovalOutWithPaging(selectCriteria);
        for(int i=0 ; i<approvalList.size();i++){
            approvalList.get(i).setCategory(approvMapper.selectCategory(approvalList.get(i).getApprovNo()));
            approvalList.get(i).setStatus(approvMapper.selectLatestStatus(approvalList.get(i).getApprovNo()));
            System.out.println(approvalList.get(i).getCategory());
            System.out.println(approvalList.get(i).getStatus());
        }
        return approvalList;
    }

    public int selectApprovOutTotal() {
        int result = approvMapper.selectApprovOutTotal();

        return result;
    }

//    public Object insertApprovOrders(List<ApprovOrderDTO> orders) {
//        int result = approvMapper.insertApprovOrders(orders);
//        return (result>0)?"결재순서 등록 성공": "결재순서 등록 실패";
//    }
//
//    public Object insertLine(ApprovLineDTO line) {
//        int result =0;
//
//        result = approvMapper.insertLine(line);
//
//        return(result > 0 ) ? "라인등록성공" : "라인등록실패";
//    }
//    public Object insertApprovLines(List<ApprovLineDTO> lines) {
//        int result = approvMapper.insertApprovLines(lines);
//        return (result > 0) ? "결재라인 등록 성공" : "결재라인 등록 실패";
//    }

//    public Object deleteApproval(ApprovalDTO approval) {
//        int result = 0;
//        int approvResult = approvMapper.deleteApproval(approval.getApprovNo());
//
//        int fileResult = 0;
//        List<ApprovFileDTO> files = approval.getApprovFileDTOList();
//
//        if(!files.isEmpty()){
//            fileResult = approvMapper.deleteFile(approval.getApprovNo());
//        }
//        int contentResult = 0;
//        List<ApprovContentDTO> content = approval.getApprovContentDTOList();
//
//        if(!content.isEmpty()){
//            contentResult = approvMapper.deleteContent(approval.getApprovNo());
//        }
//        result = approvResult+fileResult+contentResult;
//
//        return (result>2) ? "결재서류 삭제성공": "결재서류 삭제 실패";
//    }

}
