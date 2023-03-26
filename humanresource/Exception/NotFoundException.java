package com.loung.semof.humanresource.Exception;

/**
 * @파일이름 : NotFoundException.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : NotFoundException은 클라이언트가 요청한 자원을 서버에서 찾을 수 없을 때 발생했을 때 예외를 처리하는 클래스
 */
public class NotFoundException extends RuntimeException{

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : NotFoundException을 호출하면 message를 전달하는 메소드
     */
    public NotFoundException(String message) {
        super(message);
    }
}
