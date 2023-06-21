package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.VacancyDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Vacancy;
import com.example.eventsystem.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Malikov Azizjon  *  16.01.2023  *  17:39   *  IbratClub
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vacancy")
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal Employee employee) {
        ApiResponse<List<Vacancy>> response = vacancyService.getAll(employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add( @RequestBody VacancyDTO vacancyDTO, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = vacancyService.add(vacancyDTO, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Vacancy> response = vacancyService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@RequestBody VacancyDTO vacancyDTO, @PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = vacancyService.edit(vacancyDTO, id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = vacancyService.delete(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
