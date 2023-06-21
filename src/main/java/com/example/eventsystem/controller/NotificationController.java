package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Notification;
import com.example.eventsystem.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal Employee employee){
        ApiResponse<Page<Notification>> response = notificationService.getAll(page, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


}
