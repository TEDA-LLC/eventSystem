package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.SiteDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Site;
import com.example.eventsystem.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/site")
@RequiredArgsConstructor
public class SiteController {
    private final SiteService siteService;

    public ResponseEntity<?> getAll(@AuthenticationPrincipal Employee employee) {
        ApiResponse<List<Site>> response = siteService.getAll(employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Site> response = siteService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody SiteDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Site> response = siteService.add(dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody SiteDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Site> response = siteService.edit(id, dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Employee employee){
        ApiResponse<Site> response = siteService.delete(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
