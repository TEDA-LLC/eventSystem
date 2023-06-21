package com.example.eventsystem.service.authorization;

import com.example.eventsystem.dto.ApiResponse;
import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.enums.RoleType;
import com.example.eventsystem.repository.EmployeeRepository;
import com.example.eventsystem.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  11:28   *  tedaSystem
 */

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employeeOptional = employeeRepository.findByUsername(username.trim());

        return employeeOptional.orElse(null);
    }

    public ApiResponse<Employee> getMe(Employee employee) {
        if (employee != null) {
            return ApiResponse.<Employee>builder()
                    .status(200)
                    .message("It's you")
                    .data(employee)
                    .success(true)
                    .build();
        }
        return ApiResponse.<Employee>builder()
                .status(400)
                .message("Not found")
                .success(false)
                .build();
    }

    @SneakyThrows
    public ApiResponse<?> changeRole(Employee employee, String role) {
        if (employee == null) {
            throw new AccessDeniedException("");
        }
        for (RoleType userRole : employee.getRoles()) {
            if (userRole.toString().equals(role)) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("role", RoleType.valueOf(role));
                employee.setSelectedRole(RoleType.valueOf(role));
                employeeRepository.save(employee);
                return ApiResponse.builder()
                        .status(200)
                        .message("Success")
                        .data(jwtProvider.generateToken(employee, claims))
                        .success(true)
                        .build();
            }
        }
        throw new AccessDeniedException("");
    }
}
