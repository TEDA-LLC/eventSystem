package com.example.eventsystem.repository;

import com.example.eventsystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByCompany_Id(Long departmentId);

    Optional<Department> findByNameAndCompany_Id(String name, Long companyId);
    Optional<Department> findByIdAndCompany_Id(Long id, Long companyId);
}