package com.loung.semof.todo.service;

import com.loung.semof.humanresource.Exception.NotFoundException;
import com.loung.semof.todo.dao.TodoMapper;
import com.loung.semof.todo.dto.TodoDto;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {

    private final TodoMapper todoMapper;

    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    public List<TodoDto> selectTodayTodoList(Long empNo) {

        return todoMapper.selectTodayTodoList(empNo);

    }

    public List<TodoDto> selectIntendedTodoList(Long empNo) {

        return todoMapper.selectIntendedTodoList(empNo);

    }

    public TodoDto selectTodoDetail(Long todoNo) throws NotFoundException{

        TodoDto todo = todoMapper.selectTodoDetail(todoNo);

        if (todo == null) {
            throw new NotFoundException("할 일 상세보기 조회 실패");
        }
        return todo;
    }

    public List<TodoDto> selectSearchTodo(String searchWord, String empNo) {

        System.out.println(searchWord);
        System.out.println(empNo);

        System.out.println(todoMapper.selectSearchTodo(searchWord, empNo));

        return todoMapper.selectSearchTodo(searchWord, empNo);

    }

    public List<TodoDto> selectCategoryList(Long empNo) {

        List<TodoDto> categoryList = todoMapper.selectCategoryList(empNo);

        if (categoryList.isEmpty()) {
            throw new NotFoundException("카테고리 조회 실패");
        }

        return categoryList;
    }

    public String insertCategory(TodoDto categoryDto) throws SQLException {

        int result = todoMapper.insertCategory(categoryDto);

        if (result != 1) {
            throw new SQLException("카테고리 생성 실패");
        }

        return "카테고리 생성 성공";
    }

    public String updateCategory(TodoDto categoryDto) throws SQLException {

        int result = todoMapper.updateCategory(categoryDto);

        if (result != 1) {
            throw new SQLException("카테고리 수정 실패");
        }

        return "카테고리 수정 성공";
    }

    public String deleteCategory(Long cateNo) throws SQLException {

        int result = todoMapper.deleteCategory(cateNo);

        if (result != 1) {
            throw new SQLException("카테고리 삭제 실패");
        }

        return "카테고리 삭제 성공";
    }

    public String insertTodo(TodoDto todoDto) throws SQLException {

        System.out.println("Service 호출");

        if (todoDto.getTodoDate() == null || todoDto.getTodoDate().length() == 0) {

            LocalDateTime now = LocalDateTime.now();
            // 포맷 정의
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 포맷 적용
            todoDto.setTodoDate((now.format(formatter)));
        }

        if (todoDto.getTodoTime() == null || todoDto.getTodoTime().length() == 0) {
            todoDto.setTodoTime(("23:00:00"));
        }

        int result = todoMapper.insertTodo(todoDto);

        if (result != 1) {
            throw new SQLException("할 일 생성 실패");
        }

        return "할 일 생성 성공";
    }

    public String updateTodo(TodoDto todoDto) throws SQLException {

        int result = todoMapper.updateTodo(todoDto);

        if (result != 1) {
            throw new SQLException("할 일 수정 실패");
        }

        return "할 일 수정 성공";
    }

    public String deleteTodo(Long todoNo) throws SQLException {

        int result = todoMapper.deleteTodo(todoNo);

        if (result != 1) {
            throw new SQLException("할 일 삭제 실패");
        }

        return "할 일 삭제 성공";
    }

    public String updateStar(Long todoNo, Long changeStar) throws SQLException {

//        int todoStar = todoMapper.checkStar(todoNo);
//        System.out.println("todoStar" + todoStar);
//
//        Long changeStar = (long) (todoStar == 0 ? 1: 0);
//        System.out.println("changeStar" + changeStar);

        int result = todoMapper.updateStar(todoNo, changeStar);

        if (result != 1) {
            throw new SQLException("중요 표시 변경 실패");

        }

        return "중요 표시 변경 성공";
    }

    public String updateFinish(Long todoNo, Long changeFinish) throws SQLException {

//        int todoFinish = todoMapper.checkFinish(todoNo);
//        System.out.println("todoFinish" + todoFinish);
//
//        Long changeFinish = (long) (todoFinish == 0 ? 1: 0);
//        System.out.println("changeFinish" + changeFinish);

        int result = todoMapper.updateFinish(todoNo, changeFinish);

        if (result != 1) {
            throw new SQLException("완료 여부 표시 변경 실패");

        }

        return "완료 여부 표시 변경 성공";
    }


}
