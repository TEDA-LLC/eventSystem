package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.OrderDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Order;
import com.example.eventsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mansurov Abdusamad  *  14.12.2022  *  10:14   *  tedaSystem
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam Boolean active,
                                    @RequestParam Boolean ready, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Page<Order>> response = orderService.getAll(page, active, ready, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Order> response = orderService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getAllByClient")
    public ResponseEntity<?> getAllByClient(@RequestParam Long clientId, @AuthenticationPrincipal Employee employee){
        ApiResponse<List<Order>> response = orderService.getAllByClient(clientId, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getAllByEmployee")
    public ResponseEntity<?> getAllByEmployee(@RequestParam Long employeeId, @AuthenticationPrincipal Employee employee){
        ApiResponse<List<Order>> response = orderService.getAllByEmployee(employeeId, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/getAllByReceiver")
    public ResponseEntity<?> getAllByReceiver(@RequestParam Long receiverId, @AuthenticationPrincipal Employee employee){
        ApiResponse<List<Order>> response = orderService.getAllByReceiver(receiverId, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody OrderDTO dto, @AuthenticationPrincipal Employee receiver) {
        ApiResponse<Order> response = orderService.add(dto, receiver);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable(name = "id") Long orderId, @RequestBody OrderDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Order> response = orderService.edit(orderId, dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}





