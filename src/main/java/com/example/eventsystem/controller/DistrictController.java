package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.DistrictDTO;
import com.example.eventsystem.model.District;
import com.example.eventsystem.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/district")
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Long regionId) {
        ApiResponse<List<District>> response = districtService.getAll(regionId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        ApiResponse<District> response = districtService.getOne(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody DistrictDTO dto) {
        ApiResponse<?> response = districtService.add(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody DistrictDTO dto){
        ApiResponse<District> response = districtService.edit(id, dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse<?> response=districtService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
