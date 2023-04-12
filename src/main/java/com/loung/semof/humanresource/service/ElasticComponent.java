package com.loung.semof.humanresource.service;

import com.loung.semof.common.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class ElasticComponent {

    private final HumanResourceService humanResourceService;

    public ElasticComponent(HumanResourceService humanResourceService) {
        this.humanResourceService = humanResourceService;
    }

    @PostConstruct
    public void init() {
        List<EmployeeDto>  totalEmployee = humanResourceService.selectAllEmployees();
        for (EmployeeDto employee : totalEmployee) {
            log.info("사원 성별 비율 (genders)" + employee.getGender());
            log.info("사원 직급 비율 (job)" + employee.getJobCode());
            log.info("사원 이름 (name)" + employee.getEmpName());
        }
    }
}
