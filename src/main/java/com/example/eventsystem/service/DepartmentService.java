package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Bot;
import com.example.eventsystem.model.Department;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Site;
import com.example.eventsystem.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public ApiResponse<List<Department>> getAll(Employee employee) {
        List<Department> departmentList = departmentRepository.findAllByCompany_Id(employee.getCompany().getId());
        return ApiResponse.<List<Department>>builder().
                message("Here").
                status(200).
                success(true).
                data(departmentList).
                build();
    }

    public ApiResponse<Department> getOne(Long id, Employee employee) {
        Optional<Department> departmentOptional = departmentRepository.findByIdAndCompany_Id(id, employee.getCompany().getId());
        if (departmentOptional.isEmpty()) {
            return ApiResponse.<Department>builder().
                    message("Department not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<Department>builder().
                message("Here").
                status(200).
                success(true).
                data(departmentOptional.get()).
                build();
    }

    public ApiResponse<Department> add(String name, Employee employee) {
        Optional<Department> departmentOptional = departmentRepository.findByNameAndCompany_Id(name, employee.getCompany().getId());
        if (departmentOptional.isPresent()) {
            return ApiResponse.<Department>builder().
                    message("Department has with this name.Please enter another name!!!").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<Department>builder().
                message("Saved").
                status(201).
                success(true).
                data(departmentRepository.save(Department.builder().
                        name(name).
                        company(employee.getCompany()).
                        build())).
                build();
    }

    public ApiResponse<Department> edit(Long id, String name, Employee employee) {
        Optional<Department> departmentOptional = departmentRepository.findByIdAndCompany_Id(id, employee.getCompany().getId());
        if (departmentOptional.isEmpty()) {
            return ApiResponse.<Department>builder().
                    message("Department not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Department department = departmentOptional.get();
        Optional<Department> departmentOptionalByName = departmentRepository.findByNameAndCompany_Id(name, employee.getCompany().getId());
        if (departmentOptionalByName.isPresent() && !departmentOptionalByName.get().getId().equals(department.getId())) {
            return ApiResponse.<Department>builder().
                    message("Department has with this name.Please enter another name!!!").
                    status(400).
                    success(false).
                    build();
        }
        department.setName(name);
        return ApiResponse.<Department>builder().
                message("Edited").
                status(200).
                success(true).
                data(departmentRepository.save(department)).
                build();
    }

    public ApiResponse<?> delete(Long id, Employee employee) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isEmpty() || !departmentOptional.get().getCompany().getId().equals(employee.getCompany().getId())){
            return ApiResponse.builder().
                    message("Department not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Department department = departmentOptional.get();
        department.setActive(!department.isActive());
        Bot bot = department.getBot();
        bot.setActive(department.isActive());
        Site site = department.getSite();
        site.setActive(department.isActive());
        department.setBot(bot);
        department.setSite(site);
        departmentRepository.save(department);
        return ApiResponse.<Department>builder().
                message("Deleted").
                status(200).
                success(true).
                build();
    }
}
