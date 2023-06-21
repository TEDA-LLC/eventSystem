package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.RegionDTO;
import com.example.eventsystem.model.Region;
import com.example.eventsystem.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/region")
public class RegionController {
    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Long countryId) {
        ApiResponse<List<Region>> response = regionService.getAll(countryId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        ApiResponse<Region> response = regionService.getOne(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody RegionDTO regionDTO) {
        ApiResponse<?> response = regionService.add(regionDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody RegionDTO dto){
        ApiResponse<?> response = regionService.edit(id, dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse<?> response = regionService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
