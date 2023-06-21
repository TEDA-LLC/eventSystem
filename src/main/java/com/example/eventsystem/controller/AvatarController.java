package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.AvatarDTO;
import com.example.eventsystem.model.Avatar;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mansurov Abdusamad  *  07.12.2022  *  12:06   *  tedaSystem
 */
@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Page<Avatar>> response = avatarService.getAll(page, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Avatar> response = avatarService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody AvatarDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Avatar> response = avatarService.add(dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody AvatarDTO avatarDTO, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Avatar> response = avatarService.edit(id, avatarDTO, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
