package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Country;
import com.example.eventsystem.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/country")
public class CountryController {
    private final CountryService countryService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        ApiResponse<List<Country>> response = countryService.getAll();
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
    @GetMapping("/resident")
    public ResponseEntity<?> getResident(){
        ApiResponse<Country> response= countryService.getResident();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        ApiResponse<Country> response = countryService.getOne(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

//    @PreAuthorize("hasAnyAuthority('DIRECTOR','MANAGER')")
//    @PreAuthorize("hasAnyAuthority('DIRECTOR','MANAGER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Country country) {
        ApiResponse<?> response = countryService.add(country);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody Country dto) {
        ApiResponse<?> response = countryService.edit(id, dto);
        return ResponseEntity.status(response.isSuccess() ? 205 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse<?> response = countryService.deleteOne(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}
