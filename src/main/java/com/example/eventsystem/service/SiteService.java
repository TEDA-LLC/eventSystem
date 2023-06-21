package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.SiteDTO;
import com.example.eventsystem.model.Department;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Site;
import com.example.eventsystem.repository.DepartmentRepository;
import com.example.eventsystem.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  24.11.2022  *  10:28   *  tedaUz
 */
@Service
@RequiredArgsConstructor
public class SiteService {
    private final SiteRepository siteRepository;
    private final DepartmentRepository departmentRepository;

    public ApiResponse<List<Site>> getAll(Employee employee) {
        List<Site> siteList = siteRepository.findAllByDepartment_Company_Id(employee.getCompany().getId());
        return ApiResponse.<List<Site>>builder().
                message("Here").
                success(true).
                status(200).
                data(siteList).
                build();
    }

    public ApiResponse<Site> getOne(Long id, Employee employee) {
        Optional<Site> siteOptional = siteRepository.findById(id);
        if (siteOptional.isEmpty() || !siteOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Site>builder().
                    message("Not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        return ApiResponse.<Site>builder().
                message("Here").
                status(200).
                success(true).
                data(siteOptional.get()).
                build();
    }

    public ApiResponse<Site> add(SiteDTO dto, Employee employee) {
        Optional<Department> departmentOptional = departmentRepository.findById(dto.getDepartmentId());
        if (departmentOptional.isEmpty() || !departmentOptional.get().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Site>builder().
                    message("Department not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Department department = departmentOptional.get();
        Site site = new Site();
        site.setName(dto.getName());
        site.setLink(dto.getLink());
        site.setDescription(dto.getDescription());
        site.setDepartment(department);
        Site save = siteRepository.save(site);
        department.setSite(save);
        departmentRepository.save(department);
        return ApiResponse.<Site>builder().
                message("Saved!!!").
                status(201).
                success(true).
                data(save).
                build();
    }

    public ApiResponse<Site> edit(Long id, SiteDTO dto, Employee employee) {
        Optional<Department> departmentOptional = departmentRepository.findById(dto.getDepartmentId());
        if (departmentOptional.isEmpty() || !departmentOptional.get().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Site>builder().
                    message("Department not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Optional<Site> siteOptional = siteRepository.findById(id);
        Department department = departmentOptional.get();
        if (siteOptional.isEmpty() || !department.getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Site>builder().
                    message("Site not found!!!").
                    status(400).
                    success(false).
                    build();
        }
        Site site = siteOptional.get();
        site.setName(dto.getName());
        site.setLink(dto.getLink());
        site.setDescription(dto.getDescription());
        site.setDepartment(department);
        Site save = siteRepository.save(site);
        department.setSite(save);
        departmentRepository.save(department);
        return ApiResponse.<Site>builder().
                message("Saved!!!").
                status(201).
                success(true).
                data(save).
                build();
    }

    public ApiResponse<Site> delete(Long id, Employee employee) {
        Optional<Site> siteOptional = siteRepository.findById(id);
        if (siteOptional.isEmpty() || !siteOptional.get().getDepartment().getCompany().getId().equals(employee.getCompany().getId())) {
            return ApiResponse.<Site>builder().
                    message("Site not found!!!").
                    status(400).
                    success(false).
                    build();
        }

        Site site = siteOptional.get();
        site.setActive(false);
        siteRepository.save(site);
        return ApiResponse.<Site>builder().
                message("Deleted!!!").
                status(200).
                success(true).
                build();
    }
}
