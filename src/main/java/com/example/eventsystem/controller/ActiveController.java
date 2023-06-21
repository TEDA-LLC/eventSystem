package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.ActivityStatus;
import com.example.eventsystem.service.ActivityStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Mansurov Abdusamad  *  15.12.2022  *  10:45   *  tedaSystem
 */
@RestController
@RequestMapping("/active")
@RequiredArgsConstructor
public class ActiveController {
    private final ActivityStatusService activityStatusService;
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page){
        ApiResponse<Page<ActivityStatus>> response =activityStatusService.getAll(page);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        ApiResponse<ActivityStatus> response = activityStatusService.getOne(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getByUser/{id}")
    public ResponseEntity<?> getByUser(@PathVariable Long id){
        ApiResponse<List<ActivityStatus>> response = activityStatusService.getByUser(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestParam String activeType, @RequestParam Long id){
        ApiResponse<ActivityStatus> response = activityStatusService.add(activeType, id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestParam Long clientId,@RequestParam String activeType){
        ApiResponse<ActivityStatus> response = activityStatusService.edit(id, activeType, clientId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        ApiResponse<?> response = activityStatusService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
