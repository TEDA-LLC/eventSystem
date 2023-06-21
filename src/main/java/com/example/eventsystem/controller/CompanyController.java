package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.CompanyDTO;
import com.example.eventsystem.model.Company;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.repository.CompanyRepository;
import com.example.eventsystem.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author Malikov Azizjon    ourSystem    26.12.2022    15:39
 */
@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    private final CompanyRepository companyRepository;



    @GetMapping
    public ResponseEntity<?> getAllByCompany(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String name, @RequestParam(defaultValue = "true") Boolean active) {
        ApiResponse<Page<Company>> response = companyService.getAll(page, active, name);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<Company> response = companyService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLIst(){
        ApiResponse<List<Company>> response = companyService.getAllList();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@ModelAttribute CompanyDTO dto) {
        ApiResponse<?> response = companyService.add(dto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @ModelAttribute CompanyDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = companyService.edit(id, dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(@RequestParam Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false)
                    .message("Not authorization")
                    .build());
        }
        return ResponseEntity.ok().body(companyOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = companyService.delete(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/editPhoto")
    public ResponseEntity<?> editPhoto(@RequestParam Long id, @RequestBody MultipartFile multipartFile){
        ApiResponse<?> response = companyService.editPhoto(id, multipartFile);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @GetMapping("/photo")
    public ResponseEntity<?> getPhoto(@RequestParam Long id){
        return companyService.getPhoto(id);
    }
}
