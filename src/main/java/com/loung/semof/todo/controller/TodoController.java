package com.loung.semof.todo.controller;

import com.loung.semof.common.ResponseDto;
import com.loung.semof.todo.dto.TodoDto;
import com.loung.semof.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * @파일이름 : TodoController.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023/03/21
 * @작성자 : 박지희
 * @클래스설명 : 설명을 여기에 작성한다.
 */

@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * @작성일 : 2023/03/21
     * @작성자 : 박지희
     * @메소드설명 : 로그인한 사원의 할일리스트를 모두 가져오는 메소드
     */
    @GetMapping
    public ResponseEntity<ResponseDto> selectTodoList(@RequestParam Long empNo){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "전체 할 일 조회 성공", todoService.selectTodoList(empNo)));

    }

    @GetMapping("/{todoNo}")
    public ResponseEntity<ResponseDto> selectTodo(@PathVariable int todoNo){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 상세 조회 성공", todoService.selectTodoDetail(todoNo)));
    }

    @PostMapping("/category")
    public ResponseEntity<ResponseDto> insertCategory(@ModelAttribute TodoDto categoryDto){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 상세 조회 성공", todoService.insertCategory(categoryDto)));
    }

    @PutMapping(value="/category")
    public ResponseEntity<ResponseDto> updateCategory(@ModelAttribute TodoDto categoryDto){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 상세 조회 성공", todoService.updateCategory(categoryDto)));
    }

    @DeleteMapping(value="/category")
    public ResponseEntity<ResponseDto> deleteCategory(@ModelAttribute TodoDto categoryDto){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 상세 조회 성공", todoService.deleteCategory(categoryDto)));
    }


}
