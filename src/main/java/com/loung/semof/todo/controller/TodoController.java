package com.loung.semof.todo.controller;

import com.loung.semof.common.ResponseDto;
import com.loung.semof.todo.dto.TodoDto;
import com.loung.semof.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;


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
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * @작성일 : 2023/03/21
     * @작성자 : 박지희
     * @메소드설명 : 로그인한 사원의 할 일을 전체 조회하는 메소드
     */
    @GetMapping("/todolist/today/{empNo}")
    public ResponseEntity<ResponseDto> selectTodayTodoList(@PathVariable Long empNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 전체 조회 성공", todoService.selectTodayTodoList(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @GetMapping("/todolist/intended/{empNo}")
    public ResponseEntity<ResponseDto> selectIntendedTodoList(@PathVariable Long empNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 전체 조회 성공", todoService.selectIntendedTodoList(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }


    @GetMapping("/todo/{todoNo}")
    public ResponseEntity<ResponseDto> selectTodoDetail(@PathVariable Long todoNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 상세 조회 성공", todoService.selectTodoDetail(todoNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @GetMapping("/todo/search")
    @CrossOrigin("*")
    public ResponseEntity<ResponseDto> selectSearchTodo(@RequestParam(name="s") String searchWord, @RequestParam(name="e") String empNo){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 검색 성공", todoService.selectSearchTodo(searchWord, empNo)));
//        try {
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));
//
//        }
    }


    @GetMapping("/category/{empNo}")
    public ResponseEntity<ResponseDto> selectCategoryList(@PathVariable Long empNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "카테고리 조회 성공", todoService.selectCategoryList(empNo)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PostMapping("/category")
    public ResponseEntity<ResponseDto> insertCategory(@ModelAttribute TodoDto categoryDto){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "카테고리 생성 성공", todoService.insertCategory(categoryDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PutMapping(value="/category")
    public ResponseEntity<ResponseDto> updateCategory(@ModelAttribute TodoDto categoryDto){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "카테고리 수정 성공", todoService.updateCategory(categoryDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @DeleteMapping(value="/category/{cateNo}")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable Long cateNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "카테고리 삭제 성공", todoService.deleteCategory(cateNo)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PostMapping("/todo")
    public ResponseEntity<ResponseDto> insertTodo(@ModelAttribute TodoDto todoDto){

        try {
            System.out.println("Controller 호출");
            System.out.println("todoDto 확인 : " + todoDto);
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 추가 성공", todoService.insertTodo(todoDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PutMapping(value="/todo")
    public ResponseEntity<ResponseDto> updateTodo(@ModelAttribute TodoDto todoDto){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 수정 성공", todoService.updateTodo(todoDto)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @DeleteMapping(value="/todo/{todoNo}")
    public ResponseEntity<ResponseDto> deleteTodo(@PathVariable Long todoNo) throws SQLException {

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "할 일 삭제 성공", todoService.deleteTodo(todoNo)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

    @PutMapping(value="/star/{todoNo}")
    public ResponseEntity<ResponseDto> updateStar(@PathVariable Long todoNo){

        try {
            return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "중요 표시 변경 성공", todoService.updateStar(todoNo)));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생", null));

        }
    }

}
