package com.example.eventsystem.controller;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.dto.LoginDTO;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.security.JwtProvider;
import com.example.eventsystem.service.authorization.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
//        UserDetails userDetails = authService.loadUserByUsername(loginDTO.getUsername());
//        if (userDetails != null && encoder.matches(loginDTO.getPassword(), userDetails.getPassword())) {
////        if (userDetails != null) {
//            String token = jwtProvider.generateToken(userDetails);
//            return ResponseEntity.ok((ApiResponse.builder()
//                    .success(true)
//                    .data(token)
//                    .message("Success authentication")
//                    .build()));
//        }
        return ResponseEntity.ok().body(ApiResponse.builder()
                .success(true)
                .data(jwtProvider.generateToken((UserDetails) authenticate.getPrincipal()))
                .message("Success authentication")
                .build());
    }

    @PatchMapping("/role")
    public ResponseEntity<?> changeRole(@AuthenticationPrincipal Employee employee, @RequestParam String role) {
        ApiResponse<?> response = authService.changeRole(employee, role);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getMe(@AuthenticationPrincipal Employee employee) {
        ApiResponse<Employee> response = authService.getMe(employee);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
