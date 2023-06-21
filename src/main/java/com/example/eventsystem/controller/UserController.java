package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.CustomPage;
import com.example.eventsystem.dto.UserDTO;
import com.example.eventsystem.dto.response.UserResponse;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.User;
import com.example.eventsystem.repository.UserRepository;
import com.example.eventsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.glassfish.jersey.internal.inject.Custom;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllByCompany(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "false") boolean desc,
                                             @RequestParam(defaultValue = "registeredTime") String sortBy,
                                             @AuthenticationPrincipal Employee employee,
                                             @RequestParam(defaultValue = "null") Boolean active) {
        ApiResponse<CustomPage<UserResponse>> response = userService.getAll(desc, sortBy, page, employee, active);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getByQ")
    public ResponseEntity<?> getByPhoneOrName(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) String phone,
                                              @AuthenticationPrincipal Employee employee) {
        ApiResponse<List<User>> response = userService.getByPhoneOrName(name, phone, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<UserResponse> response = userService.getOne(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLIst() {
        ApiResponse<List<User>> response = userService.getAllList();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid UserDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = userService.add(dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@Valid @RequestBody UserDTO dto, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = userService.edit(id, dto, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(@RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false)
                    .message("Not authorization")
                    .build());
        }
        return ResponseEntity.ok().body(userOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Employee employee) {
        ApiResponse<?> response = userService.delete(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @DeleteMapping("/completeShutdown/{id}")
    public ResponseEntity<?> completeShutdown(@PathVariable Long id, @AuthenticationPrincipal Employee employee){
        ApiResponse<?> response = userService.completeShutdown(id, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<?> getCallCountByUser(@PathVariable Long userId, @AuthenticationPrincipal Employee employee){
        ApiResponse<?> response = userService.getCallCountByUser(userId, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getUserCountByEmployee")
    public ResponseEntity<?> getUserCountByEmployee(@RequestParam(required = false) Long employeeId, @AuthenticationPrincipal Employee employee){
        ApiResponse<?> response = userService.getUserCountByEmployee(employeeId, employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PutMapping("/removeUserEmployee")
    public ResponseEntity<?> removeUserEmployee(@RequestParam Long employeeId, @AuthenticationPrincipal Employee employee, @RequestParam Long number){
        ApiResponse<?> response = userService.removeUserEmployee(employeeId, employee, number);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/addUserEmployee")
    public ResponseEntity<?> addUserEmployee(@RequestParam Long employeeId, @AuthenticationPrincipal Employee employee, @RequestParam Long number){
        ApiResponse<?> response = userService.addUserEmployee(employeeId, employee, number);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


}

