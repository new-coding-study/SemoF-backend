package com.loung.semof.approval.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApprovServiceTest {

    @Autowired
    private ApprovService approvService;

    @Test
    public void testInit(){
        assertNotNull(approvService);
    }

    @Test
    void insertApprovLine() {
    }

    @Test
    void insertOpinion() {
    }

    @Test
    void insertApproval() {
    }

    @Test
    void selectApprovalListWithPaging() {
        //given
        //when
//        List<>
    }

    @Test
    void selectApprovalTotal() {
    }

    @Test
    void selectApprovalLineTotal() {
    }

    @Test
    void selectApprovLineListWithPaging() {
    }

    @Test
    void selectApproval() {
    }

    @Test
    void updateApproval() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void updateApprovLine() {
    }

    @Test
    void deleteApprovLine() {
    }

    @Test
    void deleteApproval() {
    }
}