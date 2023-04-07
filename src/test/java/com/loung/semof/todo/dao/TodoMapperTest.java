package com.loung.semof.todo.dao;

import com.loung.semof.todo.dto.TodoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 파일이름 : TodoMapperTest
 *
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023/04/06
 * @작성자 : 박지희
 * @클래스설명 : 설명을 여기에 작성한다.
 */
@SpringBootTest
class TodoMapperTest {

    @Autowired
    private TodoMapper todoMapper;

    @Test
    public void testInit(){
        assertNotNull(todoMapper);
    }

    @Test
    void selectSearchTodo() {

        //given
        String searchWord = "이제";
        String empNo = "41";

        //when
//        List<TodoDto> todoSearchDtoList = todoMapper.selectSearchTodo("이제", "41"); // 결과 정상 출력
        List<TodoDto> todoSearchDtoList = todoMapper.selectSearchTodo(searchWord, empNo);

        //then
        System.out.println(searchWord);
        System.out.println(empNo);
        System.out.println("--------------------------------------------------------------------");
        todoSearchDtoList.forEach(todoDto -> System.out.println("attendanceDto = " + todoDto));  //로그포제이 안 쓰고 그냥 출력문으로 확인
        // System.out.println(attendanceDtoList);
        assertNotNull(todoSearchDtoList);
    }
}