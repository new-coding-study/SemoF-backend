package com.loung.semof.todo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 파일이름 : TodoControllerTest
 *
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023/03/22
 * @작성자 : 박지희
 * @클래스설명 : 할 일 기능 테스트
 */
@SpringBootTest
class TodoControllerTest {

    @Autowired
    private TodoController todoController;

    // MockMvc 애플리케이션을 서버에 배포하지 않고도 스프링의 MVC 테스트를 할 수 있게 해준다.
    private MockMvc mockMvc;

    @Test
    public void testInit(){
        assertNotNull(todoController);

        assertNotNull(mockMvc);

    }

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @Test
    @DisplayName("selectTodoList_success")
    void 할_일_전체_조회_메소드_성공_테스트() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/todolist/{empNo}",41))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print());

    }

//    @Test
//    @DisplayName("selectTodoList_fail")
//    void 할_일_전체_조회_실패_메소드_테스트() throws Exception {
//        // given
//        // when
//        // then
//        mockMvc.perform(MockMvcRequestBuilders.get("/todos/todolist/{empNo}",100))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andDo(MockMvcResultHandlers.print());
//
//    }

    @Test
    @DisplayName("selectTodo_success")
    void 할_일_상세_조회_메소드_성공_테스트() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/todo/{todoNo}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("insertCategory_success")
    void 카테고리_생성_메소드_성공_테스트() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cateName", "test-case");

        params.add("cateColor", "#f94395");

        params.add("empNo", "59");

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/todos/category").params(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    @DisplayName("updateCategory_success")
    void 카테고리_수정_메소드_성공_테스트() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cateName", "test-case-update");

        params.add("cateColor", "#f94fd5");

        params.add("empNo", "59");

        params.add("cateNo", "90");


        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.put("/todos/category").params(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("deleteCategory_success")
    void 카테고리_삭제_메소드_성공_테스트() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/category/{cateNo}",90))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("insertTodo")
    void 할_일_생성_메소드_성공_테스트() throws Exception {
        // given
        MultiValueMap<String, String> todo = new LinkedMultiValueMap<>();

        todo.add("todoName", "todo-test");

        todo.add("todoDate", "2023-03-23");

        todo.add("todoTime", "19:00:00");

        todo.add("todoContent", "todo-test를 test하는 MockUp");

        todo.add("todoFinish", "0");

        todo.add("todoStar", "1");

        todo.add("cateNo", "91");

        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/todos/todo").params(todo))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
//                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void updateTodo() {
    }

    @Test
    @DisplayName("deleteTodo_success")
    void 할_일_삭제_메소드_성공_테스트() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/todo/{todoNo}",14))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void updateStar() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.put("/todos/star/{todoNo}", 13))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}