package com.loung.semof.todo.service;

import com.loung.semof.todo.dao.TodoMapper;
import com.loung.semof.todo.dto.TodoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoMapper todoMapper;

    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    public List<TodoDto> selectTodoList(Long empNo){

        return todoMapper.selectTodoList(empNo);

    }

    public TodoDto selectTodoDetail(int todoNo) {

        return todoMapper.selectTodoDetail(todoNo);
    }

    public String insertCategory(TodoDto categoryDto) {

        int result = todoMapper.insertCategory(categoryDto);

        return result == 1 ? "카테고리 생성 성공" : "카테고리 생성 실패";
    }

    public String updateCategory(TodoDto categoryDto) {

        int result = todoMapper.updateCategory(categoryDto);

        return result == 1 ? "카테고리 수정 성공" : "카테고리 수정 실패";
    }

    public String deleteCategory(TodoDto categoryDto) {

        Long cateNo = categoryDto.getCateNo();

        int result = todoMapper.deleteCategory(cateNo);

        return result == 1 ? "카테고리 삭제 성공" : "카테고리 삭제 실패";
    }
}
