package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Department;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal Employee employee){
        ApiResponse<List<Department>> response = departmentService.getAll(employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee){
        ApiResponse<Department> response = departmentService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestParam String name, @AuthenticationPrincipal Employee employee){
        ApiResponse<Department> response = departmentService.add(name, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id , @RequestParam String name, @AuthenticationPrincipal Employee employee){
        ApiResponse<Department> response = departmentService.edit(id, name, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Employee employee){
        ApiResponse<?> response = departmentService.delete(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
