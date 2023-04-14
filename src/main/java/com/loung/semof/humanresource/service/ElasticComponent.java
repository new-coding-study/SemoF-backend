package com.loung.semof.humanresource.service;

import com.loung.semof.common.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @파일이름 : ElasticComponent.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-04-13
 * @작성자 : 이현도
 * @클래스설명 : 엘라스틱 서치에 그래프를 작성하기 위한 비즈니스 로직을 수행하는 클래스
 */
@Slf4j
@Component
public class ElasticComponent {

    private static final String FLAG_FILE_PATH = "C:/var/flag/flag.txt";

    private final HumanResourceService humanResourceService;

    public ElasticComponent(HumanResourceService humanResourceService) {
        this.humanResourceService = humanResourceService;
    }

    /**
     * @작성일 : 2023-04-13
     * @작성자 : 이현도
     * @메소드설명 : 서버가 시작 될 때, 로그에 한번 출력해주는 메소드
     */
    @PostConstruct
    public void init() throws IOException {
        if (!hasAlreadySentLogs()) {
            List<EmployeeDto> totalEmployee = humanResourceService.selectAllEmployees();

            for (EmployeeDto employee : totalEmployee) {

                // 사원 성별 그래프를 출력하기 위해 사원 개인을 조회
                String genderMessage = "사원 성별 비율 (gender)" + employee.getGender();

                logstashLog(genderMessage);

                // 사원 직급 그래프를 출력하기 위해 사원 개인을 조회
                String jobMessage = "사원 직급 비율 (jobCode)" + employee.getJobCode();

                logstashLog(jobMessage);

            }

            setFlagFile();
        }
    }

    /**
     * @작성일 : 2023-04-14
     * @작성자 : 이현도
     * @메소드설명 : 새로운 사원이 추가될 때마다 해당 사원의 성별 및 직급 로그를 출력하는 메소드
     */
    public void addEmployee(EmployeeDto employee) {
        // 사원 성별 그래프를 출력하기 위해 사원 개인을 조회
        String genderMessage = "사원 성별 비율 (gender)" + employee.getGender();
        logstashLog(genderMessage);

        // 사원 직급 그래프를 출력하기 위해 사원 개인을 조회
        String jobMessage = "사원 직급 비율 (jobCode)" + employee.getJobCode();
        logstashLog(jobMessage);
    }

    /**
     * @작성일 : 2023-04-13
     * @작성자 : 이현도
     * @메소드설명 : 이미 출력되어 있는 로그인지 확인하는 메소드
     */
    private boolean hasAlreadySentLogs() {
        File flagFile = new File(FLAG_FILE_PATH);
        return flagFile.exists();
    }

    /**
     * @작성일 : 2023-04-13
     * @작성자 : 이현도
     * @메소드설명 : Flag 파일을 셋팅하는 메소드
     */
    private void setFlagFile() throws IOException {
        Files.createFile(Paths.get(FLAG_FILE_PATH));
    }

    /**
     * @작성일 : 2023-04-13
     * @작성자 : 이현도
     * @메소드설명 : 로그메세지를 위한 메소드
     */
    private void logstashLog(String message) {
        Logger logger = LoggerFactory.getLogger("ROOT");
        logger.info(message);
    }
}
