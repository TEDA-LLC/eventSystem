package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.ProductDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Product;
import com.example.eventsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Malikov Azizjon  *  16.01.2023  *  17:40   *  IbratClub
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal Employee employee) {
        ApiResponse<List<Product>> response = productService.getAll(employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/byCategory/{id}")
    public ResponseEntity<?> getAllByCategory(Long id, @AuthenticationPrincipal Employee employee){
        ApiResponse<List<Product>> response = productService.getAllByCategory(id,employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Product> response = productService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@ModelAttribute ProductDTO productDTO, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = productService.save(productDTO, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@ModelAttribute ProductDTO productDTO, @PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = productService.edit(productDTO, id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = productService.delete(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<?> getPhoto(@PathVariable Long id){
        return productService.getPhoto(id);
    }

}
