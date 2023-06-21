package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.PaymentDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Payment;
import com.example.eventsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mansurov Abdusamad  *  13.12.2022  *  14:42   *  tedaSystem
 */

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Page<Payment>> response = paymentService.getAll(page, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Payment> response = paymentService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody PaymentDTO dto, @AuthenticationPrincipal Employee receiver) {
        ApiResponse<Payment> response = paymentService.add(dto, receiver);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = paymentService.delete(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
