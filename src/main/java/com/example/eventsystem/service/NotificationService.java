package com.example.eventsystem.service;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Notification;
import com.example.eventsystem.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationService {
    @Value("${page.size}")
    private int size;
    private final NotificationRepository notificationRepository;

    public ApiResponse<Page<Notification>> getAll(int page, Employee employee) {
        if (employee == null) {
            return ApiResponse.<Page<Notification>>builder().
                    message("User not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationPage = notificationRepository.findAllByViewFalseAndCompany(employee.getCompany(), pageable);
        if (notificationPage.isEmpty()){
            return ApiResponse.<Page<Notification>>builder().
                    message("Notifications not found!!!").
                    success(false).
                    status(400).
                    build();
        }
        return ApiResponse.<Page<Notification>>builder().
                message("Here!!!").
                success(true).
                status(200).
                data(notificationPage).
                build();
    }
}
