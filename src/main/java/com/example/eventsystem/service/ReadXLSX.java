package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.*;
import com.example.eventsystem.repository.DepartmentRepository;
import com.example.eventsystem.repository.DistrictRepository;
import com.example.eventsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReadXLSX {
    private final DistrictRepository districtRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    //    @SneakyThrows
    public ApiResponse<?> excelReader(MultipartFile file, Long departmentId, Employee employee) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            Optional<Department> departmentOptional = departmentRepository.findByIdAndCompany_Id(departmentId, employee.getCompany().getId());
            if (departmentOptional.isEmpty()) {
                return ApiResponse.builder().
                        message("Department not found!!!").
                        status(400).
                        success(false).
                        build();
            }
            Department department = departmentOptional.get();
            for (Row row : sheet) {
                String name, phone, districtName, company;


                districtName = asd(row.getCell(0));
                name = asd(row.getCell(1));
                phone = asd(row.getCell(2));
                company = asd(row.getCell(3));

                Optional<User> userOptional = userRepository.findByPhoneAndDepartment_Id(phone, departmentId);
                if (userOptional.isPresent()) {
                    continue;
                }
                User user = new User();
                user.setFullName(name);
                user.setPhone(phone);
                user.setCompany(company);
                user.setDepartment(department);
                user.setEmployee(employee);
                Optional<District> districtOptional = districtRepository.findById(Long.valueOf(districtName));
                districtOptional.ifPresent(district -> user.setAddress(Address.builder().
                        district(district).
                        build()));
                userRepository.save(user);
            }
            workbook.close();
        } catch (Exception e) {
            return ApiResponse.builder().
                    message("Something went wrong!!!").
                    success(false).
                    status(400).
                    build();
        }
        return ApiResponse.builder().
                message("Success!!!").
                success(true).
                status(201).
                build();
    }


    private String asd(Cell cell) {
        if (cell == null) {
            return "";
        }
        return String.valueOf(switch (cell.getCellType()) {
            case FORMULA -> cell.getCellFormula();
            case _NONE, BLANK -> "";
            case NUMERIC -> (long) (cell.getNumericCellValue());
            case BOOLEAN -> cell.getBooleanCellValue();
            case ERROR -> cell.getErrorCellValue();
            default -> cell.getStringCellValue();
        });
    }
}
