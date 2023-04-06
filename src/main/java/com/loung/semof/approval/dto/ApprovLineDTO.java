package com.loung.semof.approval.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
/**
 * @파일이름 : ApprovLineDTO.java
 * @프로젝트 : semoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-22
 * @작성자 : 박유리
 * @클래스설명 : 설명을 여기에 작성한다.
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class ApprovLineDTO {
    private Integer lineNo;
    private String lineName;
    private String empName;
    private Integer empNo;
    private String branchName;
    private Integer branchCode;
    private List<ApprovOrderDTO> approvOrderDTOList;
//    private List<ApprovOrderDTO> approvOrderDTOList = new ArrayList<>();
//    public List<ApprovOrderDTO> getApprovOrderDTOList() {
//        return approvOrderDTOList;
//    }
//
//    public void setApprovOrderDTOList(List<ApprovOrderDTO> approvOrderDTOList) {
//        this.approvOrderDTOList = approvOrderDTOList;
//    }
//    private
}


//    @JsonCreator
//    public MyClass(@JsonProperty("myList") List<String> myList) {
//        this.myList = myList;
//    }