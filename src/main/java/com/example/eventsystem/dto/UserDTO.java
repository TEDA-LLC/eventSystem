package com.example.eventsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDTO {
    @NotEmpty
    @NotNull
    private String username, fullName, email, phone;
    private String  passportNumber;

    private String genderType;
    private Long departmentId;
    private List<String> userRoles;

    private AddressDTO addressDTO;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate brithDate;
}
