package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.UserHistory;
import com.example.eventsystem.model.WordHistory;
import com.example.eventsystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Malikov Azizjon  *  17.01.2023  *  23:05   *  IbratClub
 */

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    @GetMapping("/auth")
    public ResponseEntity<?> checkAuth(){
        return ResponseEntity.ok("Here !!!!! ");
    }
    @GetMapping("/userHistory")
    public ResponseEntity<?> getUserHistory(@AuthenticationPrincipal Employee employee) {
        ApiResponse<List<UserHistory>> response = reportService.getUserHistory(employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/amount/{productId}")
    public ResponseEntity<?> getAmountByProduct(@PathVariable Long productId, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = reportService.getAmountByProduct(productId, employee);

        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/wordHistory")
    public ResponseEntity<?> getAllHistory(@AuthenticationPrincipal Employee employee) {
        ApiResponse<List<WordHistory>> response = reportService.getWordsHistory(employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PatchMapping("/view/{requestId}")
    public ResponseEntity<?> editView(@PathVariable Long requestId, @AuthenticationPrincipal Employee employee){
        ApiResponse<?> response = reportService.editView(requestId, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
