package com.loung.semof.email.utils;

import org.springframework.core.io.InputStreamSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @파일이름 : ByteArrayResource.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-05
 * @작성자 : 이현도
 * @클래스설명 : 주어진 바이트 배열에 대한 Resource 구현을 담당하는 클래스
 */
public class ByteArrayResource implements InputStreamSource {

    private final byte[] byteArray;

    public ByteArrayResource(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.byteArray);
    }
}