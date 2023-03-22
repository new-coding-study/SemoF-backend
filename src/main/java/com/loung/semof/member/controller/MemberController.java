package com.loung.semof.member.controller;


import com.loung.semof.common.ResponseDto;
import com.loung.semof.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @파일이름 : .java
 * @프로젝트 : semoF
 * @버전관리 : 1.0.0
 * @작성일 :
 * @작성자 : 박유리
 * @클래스설명 : 설명을 여기에 작성한다.
 */
@RestController
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
/**
 * @작성일 :
 * @작성자 : 박유리
 * @메소드설명 : 설명을 여기에 작성한다.
 */
    @GetMapping("/members/{memberId}")
    public ResponseEntity<ResponseDto> selectMyMemberInfo(@PathVariable String memberId) {

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", memberService.selectMyInfo(memberId)));
    }



}
