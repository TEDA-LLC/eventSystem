package com.example.eventsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author Mansurov Abdusamad  *  12.12.2022  *  10:47   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {
    @NotNull
    private String username, fullName;
    @Length(min = 6)
    private String password;
    private String phoneFirst;
    private String phoneSecond;
    private AddressDTO addressDTO;
//    private Long companyId;
    private List<String> roleList;
}
