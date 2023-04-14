package com.loung.semof.humanresource.service;

import com.loung.semof.common.dao.BranchMapper;
import com.loung.semof.common.dao.DepartmentMapper;
import com.loung.semof.common.dao.EmployeeMapper;
import com.loung.semof.common.dto.BranchDto;
import com.loung.semof.common.dto.DepartmentDto;
import com.loung.semof.common.dto.EmployeeDto;
import com.loung.semof.humanresource.Exception.NotFoundException;
import com.loung.semof.humanresource.dao.BranchOrderMapper;
import com.loung.semof.humanresource.dao.DepartmentOrderMapper;
import com.loung.semof.humanresource.dao.HumanResourceMapper;
import com.loung.semof.humanresource.dto.BranchOrderDto;
import com.loung.semof.humanresource.dto.DepartmentOrderDto;
import com.loung.semof.humanresource.dto.EmployeePhotoDto;
import com.loung.semof.humanresource.dto.HumanResourceDto;
import com.loung.semof.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @파일이름 : HumanResourceService.java
 * @프로젝트 : SemoF
 * @버전관리 : 1.0.0
 * @작성일 : 2023-03-21
 * @작성자 : 이현도
 * @클래스설명 : HumanResourceController에서 요구하는 비즈니스 로직을 수행하는 클래스
 */
@Slf4j
@Service
public class HumanResourceService {

    private final EmployeeMapper employeeMapper;
    private final DepartmentMapper departmentMapper;
    private final BranchMapper branchMapper;
    private final HumanResourceMapper humanResourceMapper;

    private final BranchOrderMapper branchOrderMapper;

    private final DepartmentOrderMapper departmentOrderMapper;
    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

    public HumanResourceService(EmployeeMapper employeeMapper, DepartmentMapper departmentMapper, BranchMapper branchMapper, HumanResourceMapper humanResourceMapper, BranchOrderMapper branchOrderMapper, DepartmentOrderMapper departmentOrderMapper) {
        this.employeeMapper = employeeMapper;

        this.departmentMapper = departmentMapper;

        this.branchMapper = branchMapper;

        this.humanResourceMapper = humanResourceMapper;

        this.branchOrderMapper = branchOrderMapper;

        this.departmentOrderMapper = departmentOrderMapper;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원의 부서 발령 비즈니스 로직을 수행하는 메소드
     */
    public EmployeeDto updateEmployeeDepartment(Long empNo, String deptCode) throws SQLException, NotFoundException {

        log.info("[HumanResourceService] deptCode : " + deptCode);

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        log.info("[HumanResourceService] Dept employee : " + employee);

        if (employee == null) {
            throw new NotFoundException("해당 사원이 존재하지 않습니다.");
        }

        DepartmentOrderDto deptOrder = new DepartmentOrderDto();
        deptOrder.setEmpNo(employee.getEmpNo());
        deptOrder.setOrderDate(LocalDateTime.now());
        deptOrder.setDeptCode(employee.getDeptCode());

        DepartmentDto department = departmentMapper.selectDepartmentByDeptCode(deptCode);

        log.info("[HumanResourceService] department : " + department);

        if (department == null) {
            throw new NotFoundException("해당 부서가 존재하지 않습니다.");
        }

        deptOrder.setNewDeptCode(department.getDeptCode());
        departmentOrderMapper.insertDepartmentOrder(deptOrder);

        employee.setDeptCode(department.getDeptCode());
        employeeMapper.updateEmployeeDepartment(employee);

        return employee;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원의 지점 발령 비즈니스 로직을 수행하는 메소드.
     */
    public EmployeeDto updateEmployeeBranch(Long empNo, Long branchCode) throws SQLException, NotFoundException {

        log.info("[HumanResourceService] branchCode : " + branchCode);

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        log.info("[HumanResourceService] Branch employee : " + employee);

        if (employee == null) {
            throw new NotFoundException("해당 사원이 존재하지 않습니다.");
        }

        BranchOrderDto branchOrder = new BranchOrderDto();
        branchOrder.setEmpNo(employee.getEmpNo());
        branchOrder.setOrderDate(LocalDateTime.now());
        branchOrder.setBranchCode(employee.getBranchCode());

        BranchDto branch = branchMapper.selectBranchByBCode(branchCode);

        log.info("[HumanResourceService] branch : " + branch);

        if (branch == null) {
            throw new NotFoundException("해당 지점이 존재하지 않습니다.");
        }

        branchOrder.setNewBCode(branch.getBranchCode());
        branchOrderMapper.insertBranchOrder(branchOrder);

        employee.setBranchCode(branch.getBranchCode());
        employeeMapper.updateEmployeeBranch(employee);

        return employee;
    }



    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 등록 비즈니스 로직을 수행하는 메소드.
     */
    public EmployeeDto insertEmployee(EmployeeDto employeeDto) throws SQLException {
        log.info("[EmployeeService] insertEmployee Start ===================================");

        try {
            employeeMapper.insertEmployee(employeeDto);

            log.info("[EmployeeService] employeeDto : " + employeeDto);
            return employeeDto;

        } catch (Exception e) {
            e.printStackTrace();

            throw new SQLException("사원 정보를 추가하는 도중 에러가 발생하였습니다.", e);   // 예외 처리 로직
        }
    }

    /**
     * @작성일 : 2023-03-27
     * @작성자 : 이현도
     * @메소드설명 : 사원의 사진 등록 비즈니스 로직을 수행하는 메소드.
     */
    public void insertEmployeePhoto(MultipartFile employeePhoto, EmployeeDto employee) throws IOException {

        log.info("[EmployeeService insertEmployeePhoto] employee" + employee);

        // 증명사진 파일 업로드
        String imageName = UUID.randomUUID().toString().replace("-", "");

        String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, employeePhoto);

        log.info("[EmployeeService] replaceFileName : " + replaceFileName);

        // 증명사진 등록 정보 저장
        EmployeePhotoDto employeePhotoDto = new EmployeePhotoDto();

        employeePhotoDto.setEmpNo(employee.getEmpNo());

        employeePhotoDto.setFilePath(replaceFileName);

        employeePhotoDto.setOriginName(employeePhoto.getOriginalFilename());

        employeePhotoDto.setChangeName(replaceFileName);

        employeePhotoDto.setUploadDate(LocalDateTime.now());

        humanResourceMapper.insertEmployeePhoto(employeePhotoDto);
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 정보 수정 비즈니스 로직을 수행하는 메소드
     */
    public EmployeeDto updateEmployee(Long empNo, String phone, String email, String address, Integer salary, Long jobCode, MultipartFile employeePhoto) throws Exception {
        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);
        if (employee == null) {
            throw new NotFoundException("해당 사원을 찾을 수 없습니다.");
        }

        if (phone != null) {
            employee.setPhone(phone);
        }
        if (email != null) {
            employee.setEmail(email);
        }
        if (address != null) {
            employee.setAddress(address);
        }
        if (salary != null) {
            employee.setSalary(salary);
        }
        if (jobCode != null) {
            employee.setJobCode(jobCode);
        }

        // 새로운 사진이 업로드된 경우
        if (employeePhoto != null) {
            EmployeePhotoDto oldPhoto = humanResourceMapper.selectEmployeePhotoByEmpNo(empNo);
            if (oldPhoto != null) {
                FileUploadUtils.deleteFile(IMAGE_DIR, oldPhoto.getChangeName());
                humanResourceMapper.deleteEmployeePhoto(oldPhoto.getPhotoNo());
            }

            String imageName = UUID.randomUUID().toString().replace("-", "");
            String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, employeePhoto);

            EmployeePhotoDto newPhoto = EmployeePhotoDto.builder()
                    .empNo(empNo)
                    .filePath(replaceFileName)
                    .originName(employeePhoto.getOriginalFilename())
                    .changeName(replaceFileName)
                    .uploadDate(LocalDateTime.now())
                    .build();

            // DB에 저장
            humanResourceMapper.insertEmployeePhoto(newPhoto);
        }

        int affectedRows = employeeMapper.updateEmployee(employee);

        if (affectedRows != 1) {
            throw new SQLException("사원 정보 업데이트에 실패하였습니다.");
        }

        return employee;
    }


    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 퇴직으로 상태값 변경하는 비즈니스 로직을 수행하는 메소드
     */
    public EmployeeDto updateEmployeeStatus(Long empNo) throws Exception {

        EmployeeDto employee = employeeMapper.selectEmployeeByEmpNo(empNo);

        if(employee != null) {
            employee.setWorkStatus("N");

            int affectedRows = employeeMapper.updateEmployeeStatus(employee);

            if (affectedRows > 0) {
                return employee;

            } else {
                throw new Exception("사원 상태 변경에 실패했습니다.");
            }

        } else {
            throw new NotFoundException("사원을 찾을 수 없습니다.");
        }
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 :  사원의 전체 수를 조회하는 비즈니스 로직을 수행하는 메소드
     */
    public int selectEmployeeTotal() throws SQLException {

        int totalCount = 0;

        try {
            totalCount = humanResourceMapper.selectEmployeeTotal();

        } catch (Exception e) {
            throw new SQLException("전체 직원 수를 조회하지 못했습니다.", e);
        }
        return totalCount;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 전체를 조회해오고 페이지 처리를 하는 비즈니스 로직을 수행하는 메소드
     */
    public List<HumanResourceDto> selectEmployeeListWithPaging(int startRow, int endRow) {

        List<HumanResourceDto> employeeList = Collections.emptyList();

        try {
            employeeList = humanResourceMapper.selectEmployeeListWithPaging(startRow, endRow);

            log.info("[HumanResourceService] employeeList : " + employeeList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    /**
     * @작성일 : 2023-03-21
     * @작성자 : 이현도
     * @메소드설명 : 사원 조회 비즈니스 로직을 수행하는 메소드
     */
    public List<HumanResourceDto> selectEmployees(String empName, String deptName, String branchName) throws Exception {

        List<HumanResourceDto> employees = humanResourceMapper.selectEmployees(empName, deptName, branchName);

        if (employees.isEmpty()) {
            return null; // 조회된 사원이 없는 경우 null 반환
        }

        return employees;
    }

    /**
     * @작성일 : 2023-03-22
     * @작성자 : 이현도
     * @메소드설명 : 생일에 해당하는 사원 조회 비즈니스 로직을 수행하는 메소드
     */
    public List<EmployeeDto> selectEmployeeByBirthMonth() throws Exception {

        LocalDate now = LocalDate.now();    // 이번 달 날짜 정보 추출

        log.info("[HumanResourceService] LocalDate :" + now);

        int monthValue = now.getMonthValue();

        log.info("[HumanResourceService] MonthValue :" + monthValue);

        List<EmployeeDto> employees = humanResourceMapper.selectEmployeeByBirthMonth(monthValue);   // 생일인 사원 조회

        log.info("[HumanResourceService] Employees :" + employees);

        employees.sort(Comparator.comparing(e -> e.getEmpReg().substring(2, 6)));   // 생일순으로 정렬

        return employees;

        //이번 달 + 다음달 생일자
//        List<EmployeeDto> employeesAfter = humanResourceMapper.selectEmployeeByBirthMonthAfter(monthValue);  // 이번 달 이후 생일인 사원 조회
//        log.info("[HumanResourceService] employeesAfter :" + employeesAfter);
//        employeesAfter.sort(Comparator.comparing(e -> e.getEmpReg().substring(2, 6)));
//        employees.addAll(employeesAfter);   // 이번 달 생일인 사원과 이번 달 이후 생일인 사원을 합침
    }

    /**
     * @작성일 : 2023-03-23
     * @작성자 : 이현도
     * @메소드설명 : 조직도 출력에 사용 할 empName, deptName, branchName에 따라 조회를 수행하는 메소드
     */
    public List<HumanResourceDto> SelectEmployeesForChart(String empName, String deptName, String branchName) {

        List<HumanResourceDto> employees = humanResourceMapper.SelectEmployeesForChart(empName, deptName, branchName);

        if (employees.isEmpty()) {
            return null; // 조회된 사원이 없는 경우 null 반환
        }

        return employees;
    }

    /**
     * @작성일 : 2023-03-24
     * @작성자 : 이현도
     * @메소드설명 : 사원 번호와 일치하는 사원을 조회하는 비즈니스 로직
     */
    public HumanResourceDto selectEmployeeByEmpNo(Long empNo) {

            if (empNo == null) {
                throw new IllegalArgumentException("사원 번호를 입력해주세요.");
            }

            List<HumanResourceDto> result = humanResourceMapper.selectEmployeeByEmpNo(empNo);

            if (result == null || result.isEmpty()) {
                return null; // 조회된 사원이 없는 경우 null 반환
            }
            return result.get(0); // 첫 번째 사원 반환
        }


    public List<DepartmentDto> selectDepartments() {

        List<DepartmentOrderDto> deptOrders = humanResourceMapper.selectDepartmentsOrders();

        List<DepartmentDto> dept = deptOrders.stream()
                .map(DepartmentOrderDto::getDepartmentDto)
                .collect(Collectors.toList());
        return dept;
    }

    public List<BranchDto> selectBranches() {
        List<BranchOrderDto> branchOrders = humanResourceMapper.selectBranchesOrders();
        List<BranchDto> branch = branchOrders.stream()
                .map(BranchOrderDto::getBranchDto)
                .collect(Collectors.toList());
        return branch;
    }

    public EmployeePhotoDto selectEmployeePhotoByEmpNo(Long empNo) {

        return humanResourceMapper.selectEmployeePhotoByEmpNo(empNo);
    }

    /**
     * @작성일 : 2023-04-08
     * @작성자 : 이현도
     * @메소드설명 : 생일자 수를 조회하는 비즈니스 로직
     */
    public int selectBirthEmpCount() throws SQLException {

        LocalDate now = LocalDate.now();    // 이번 달 날짜 정보 추출

        log.info("[HumanResourceService] LocalDate :" + now);

        int monthValue = now.getMonthValue();

        log.info("[HumanResourceService] MonthValue :" + monthValue);

        int totalCount = 0;

        try {
            totalCount =  humanResourceMapper.selectBirthEmpCount(monthValue);

            log.info("[HumanResourceService] totalCount :" + totalCount);

        } catch (Exception e) {
            throw new SQLException("전체 직원 수를 조회하지 못했습니다.", e);
        }

        return totalCount;
    }

    /**
     * @작성일 : 2023-04-08
     * @작성자 : 이현도
     * @메소드설명 : 오늘의 출근자를 조회하는 비즈니스 로직
     */
    public int selectTodayAttendanceList() throws SQLException {

        LocalDate today = LocalDate.now();

        int todayAttendance  = 0;

        try {
            todayAttendance  =  humanResourceMapper.selectTodayAttendanceList(today);

            log.info("[HumanResourceService] totalCount :" + todayAttendance );

        } catch (Exception e) {
            throw new SQLException("출근 직원 수를 조회하지 못했습니다.", e);
        }

        return todayAttendance ;
    }

    /**
     * @작성일 : 2023-04-09
     * @작성자 : 이현도
     * @메소드설명 : 사원 연차 조회 메소드
     */
    public int selectVacationCount() throws SQLException {
        int vacationCount  = 0;

        try {
            vacationCount  =  humanResourceMapper.selectVacationCount();

            log.info("[HumanResourceService] totalCount :" + vacationCount );

        } catch (Exception e) {
            throw new SQLException("휴가자 수를 조회하지 못했습니다.", e);
        }
        return vacationCount ;
    }

    /**
     * @작성일 : 2023-04-11
     * @작성자 : 이현도
     * @메소드설명 : 엘라스틱서치를 위한 사원 전체 조회 메소드
     */
    public List<EmployeeDto> selectAllEmployees() {

        return humanResourceMapper.selectAllEmployees();
    }

    public void insertDefaultCalendar(EmployeeDto employee) {

        String calName = "기본 캘린더";
        String calColor = "#0177AA";
        String madeEmpNo = String.valueOf(employee.getEmpNo());

        humanResourceMapper.insertDefaultCalendar(calName, calColor, madeEmpNo);
    }

    public void insertDefaultCategory(EmployeeDto employee) {
        String cateName = "기본 카테고리";
        String cateColor = "#38A8F9";
        String empNo = String.valueOf(employee.getEmpNo());

        humanResourceMapper.insertDefaultCategory(cateName, cateColor, empNo);
    }

    public void insertStarCategory(EmployeeDto employee) {
        String cateName = "중요 표시한 할 일";
        String cateColor = "#F5F938";
        String empNo = String.valueOf(employee.getEmpNo());

        humanResourceMapper.insertDefaultCategory(cateName, cateColor, empNo);
    }
}
