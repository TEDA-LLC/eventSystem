package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Category;
import com.example.eventsystem.model.Department;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.repository.CategoryRepository;
import com.example.eventsystem.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Malikov Azizjon  *  16.01.2023  *  17:52   *  IbratClub
 */
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final DepartmentRepository departmentRepository;

    public ApiResponse<List<Category>> getAll(Long departmentId, Employee employee) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isEmpty() || !departmentOptional.get().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<List<Category>>builder().
                    message("Not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        List<Category> categories = categoryRepository.findAllByDepartment_Id(departmentId);
        return ApiResponse.<List<Category>>builder().
                message("Here").
                status(200).
                success(true).
                data(categories).
                build();
    }

}
