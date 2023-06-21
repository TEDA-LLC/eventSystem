package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.service.ReadXLSX;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController {

    private final ReadXLSX readXLSX;
    @PostMapping
    public ResponseEntity<?> uploadExcel(@ModelAttribute MultipartFile file, @RequestParam Long departmentId, @AuthenticationPrincipal Employee employee){
        ApiResponse<?> response = readXLSX.excelReader(file, departmentId, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
