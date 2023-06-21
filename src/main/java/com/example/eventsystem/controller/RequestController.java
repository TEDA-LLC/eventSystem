package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Request;
import com.example.eventsystem.model.SiteHistory;
import com.example.eventsystem.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:35   *  tedaSystem
 */

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;


    @GetMapping("/getRequests")
    public ResponseEntity<?> getRequest(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) Boolean view, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Page<Request>> response = requestService.getRequest(page, view, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getHistory")
    public ResponseEntity<?> getSiteHistory(@RequestParam(defaultValue = "0") int page) {
        ApiResponse<Page<SiteHistory>> response = requestService.getSiteHistory(page);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getRequest/{id}")
    public ResponseEntity<?> getOneRequest(@PathVariable Long id, @AuthenticationPrincipal Employee employee){
        ApiResponse<Request> response = requestService.getOneRequest(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getHistory/{id}")
    public ResponseEntity<?> getOneSiteHistory(@PathVariable Long id) {
        ApiResponse<SiteHistory> response = requestService.getOneSiteHistory(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PatchMapping("/request/{id}")
    public ResponseEntity<?> editRequestStatus(@PathVariable Long id, @AuthenticationPrincipal Employee employee){
        ApiResponse<Request> response = requestService.editRequestStatus(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestParam Long userId, @RequestParam Long productId, @AuthenticationPrincipal Employee employee){
        ApiResponse<Request> response = requestService.add(userId, productId, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

//    @PostMapping
//    public ResponseEntity<?> addRequest(@RequestBody RequestDTO dto, @AuthenticationPrincipal Employee employee){
//        ApiResponse<Request> response = requestService.addRequest(dto, employee);
//        return ResponseEntity.status(response.getStatus()).body(response);
//    }
    @PatchMapping
    public ResponseEntity<?> edit(@RequestParam String qrcode, @AuthenticationPrincipal Employee employee){
        ApiResponse<?> response = requestService.edit(qrcode, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
