package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.CallDTO;
import com.example.eventsystem.dto.CustomPage;
import com.example.eventsystem.dto.response.UserResponse;
import com.example.eventsystem.model.Call;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.service.CallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Mansurov Abdusamad  *  01.12.2022  *  13:46   *  tedaSystem
 */

@RestController
@RequestMapping("/call")
@RequiredArgsConstructor
public class CallController {

    private final CallService callService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Page<Call>> response = callService.getAll(page, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/getForToday")
    public ResponseEntity<?> getAllForToday(@AuthenticationPrincipal Employee employee,
                                            @RequestParam(defaultValue = "0") int page){
        ApiResponse<List<Call>> response = callService.getAllForToday(employee, page);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Call> response = callService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getbyuser/{id}")
    public ResponseEntity<?> getByUser(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
                                       @RequestParam LocalDate startDate, @RequestParam LocalDate endDate, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Page<Call>> response = callService.getByUser(id, page, startDate, endDate, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getbyclient")
    public ResponseEntity<?> getByClient(@RequestParam Long clientId, @RequestParam(defaultValue = "0") int page, @AuthenticationPrincipal Employee employee){
        ApiResponse<Page<Call>> response = callService.getByClient(clientId, page, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CallDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Call> response = callService.add(dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestParam Long id, @RequestParam CallDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Call> response = callService.edit(id, dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id, @AuthenticationPrincipal Employee employee){
        ApiResponse<?> response = callService.delete(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
