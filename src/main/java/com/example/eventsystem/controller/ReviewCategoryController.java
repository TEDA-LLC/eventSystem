package com.example.eventsystem.controller;


import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.ReviewCategory;
import com.example.eventsystem.service.ReviewCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  12:10   *  tedaSystem
 */

@RestController
@RequestMapping("/ReviewCategory")
@RequiredArgsConstructor
public class ReviewCategoryController {

    private final ReviewCategoryService reviewCategoryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        ApiResponse<List<ReviewCategory>> response = reviewCategoryService.getAll();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        ApiResponse<ReviewCategory> response = reviewCategoryService.getOne(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestParam String name) {
        ApiResponse<ReviewCategory> response = reviewCategoryService.add(name);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestParam Long id, @RequestParam String name) {
        ApiResponse<ReviewCategory> response = reviewCategoryService.edit(id, name);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id){
        ApiResponse<ReviewCategory> response = reviewCategoryService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
