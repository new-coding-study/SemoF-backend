package com.loung.semof.approval.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ApprovControllerTest {
    @Autowired
    private ApprovController approvController;
    private MockMvc mockMvc;
    @Test
    public void testInit(){
        assertNotNull(approvController);

        assertNotNull(mockMvc);
    }
    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(approvController).build();
    }
    @Test
    void insertApprovLine() throws Exception{
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("lineName","인사부");

        params.add("branchCode","3");

        params.add("line","과장, 차장, 부장");

        params.add("empNo","3");

        params.add("jobCode","4");

        //when then
        mockMvc.perform(MockMvcRequestBuilders.post("/line").params(params))
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void insertOpinion() throws Exception{
        //given
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//
//        params.add("opinContent","횟수 초과");
//
//        params.add("empNo","3");
//
//        params.add("approvNo","1");
//
//
//        //when then
//        mockMvc.perform(MockMvcRequestBuilders.post("/opinion").params(params))
//                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void insertApproval() {
    }

    @Test
    void selectApprovalListWithPaging() throws Exception{
//        then
        mockMvc.perform(MockMvcRequestBuilders.get("/approval/approvList"))
//                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void selectApprovLineListWithPaging() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/approval/lineList"))
//                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void selectApproval() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/approval/approval/{approvNo}",1))
//                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateApprovLine() {
    }

    @Test
    void updateApproval() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void deleteApprovLine() {
    }

    @Test
    void deleteApproval() {
    }
}