package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.EmployeeDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mansurov Abdusamad  *  12.12.2022  *  10:12   *  tedaSystem
 */
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(required = false) Boolean active,
                                    @RequestParam(defaultValue = "false") boolean desc,
                                    @RequestParam(defaultValue = "registered_time") String sortBy,
                                    @AuthenticationPrincipal Employee employee) {
        ApiResponse<Page<Employee>> response = employeeService.getAll(desc, sortBy, page, active, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getAllByCompany")
    public ResponseEntity<?> getAllByCompany(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "null") Boolean active, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Page<Employee>> response = employeeService.getAllByCompany(page, active, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Employee> response = employeeService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody EmployeeDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Employee> response = employeeService.add(dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody EmployeeDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Employee> response = employeeService.edit(id, dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = employeeService.delete(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editEvent(@PathVariable Long operatorId,@RequestParam Long eventId ,  @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = employeeService.editEvent(operatorId,eventId, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
