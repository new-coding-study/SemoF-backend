package com.loung.semof.humanresource.service;

import com.loung.semof.common.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ElasticComponent {

    private final HumanResourceService humanResourceService;

    public ElasticComponent(HumanResourceService humanResourceService) {
        this.humanResourceService = humanResourceService;
    }

    @PostConstruct
    public void init() {
        List<EmployeeDto> totalEmployee = humanResourceService.selectAllEmployees();

        Map<String, Integer> genderMap = new HashMap<>();

        Map<Long, Integer> jobMap = new HashMap<>();

        int totalEmployeeCount = 0;

        for (EmployeeDto employee : totalEmployee) {
            String gender = employee.getGender();

            Long jobCode = employee.getJobCode();

            genderMap.put(gender, genderMap.getOrDefault(gender, 0) + 1);

            jobMap.put(jobCode, jobMap.getOrDefault(jobCode, 0) + 1);

            totalEmployeeCount++;
        }

        log.info("사원 성별 비율 (gender)M: " + (genderMap.containsKey("M") ? String.format("%.2f", (double) genderMap.get("M") / totalEmployeeCount * 100) + "%" : "0%"));
        log.info("사원 성별 비율 (gender)F: " + (genderMap.containsKey("F") ? String.format("%.2f", (double) genderMap.get("F") / totalEmployeeCount * 100) + "%" : "0%"));

        for (Map.Entry<Long, Integer> entry : jobMap.entrySet()) {

            Long jobCode = entry.getKey();

            int count = entry.getValue();

            String jobCodeString = String.valueOf(jobCode);

            log.info("사원 직급 비율 (job)" + jobCodeString + ": " + String.format("%.2f", (double) count / totalEmployeeCount * 100) + "%");
        }

        log.info("총 사원 수: " + totalEmployeeCount);
    }

}
