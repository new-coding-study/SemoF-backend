package com.loung.semof.todo.dao;

import com.loung.semof.todo.dto.TodoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @파일이름 : TodoMapper.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023/03/21
 * @작성자 : 박지희
 * @클래스설명 :
 */

@Mapper
public interface TodoMapper {

    List<TodoDto> selectTodoList(Long empNo);
    TodoDto selectTodoDetail(Long todoNo);
    int insertCategory(TodoDto categoryDto);
    int updateCategory(TodoDto categoryDto);
    int deleteCategory(Long cateNo);
    int insertTodo(TodoDto todoDto);
    int updateTodo(TodoDto todoDto);
    int deleteTodo(Long todoNo);
    int checkStar(Long todoNo);
    int updateStar(Long todoNo, Long changeStar);
}
