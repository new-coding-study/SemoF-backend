package com.loung.semof.approval.service;

import com.loung.semof.approval.dao.ApprovalMapper;
import com.loung.semof.approval.dto.ApprovOpinDTO;
import org.springframework.stereotype.Service;

@Service
public class ApprovService {
    private final ApprovalMapper approvMapper;

    public ApprovService(ApprovalMapper approvalMapper) {
        this.approvMapper = approvalMapper;
    }

    public Object insertOpinion(ApprovOpinDTO opinion) {
        int result =0;

        result = approvMapper.insertOpinion(opinion);

        return(result > 0 ) ? "의견등록성공" : "의견등록실패";
    }
}
