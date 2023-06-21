package com.example.eventsystem.dto;

import lombok.*;

/**
 * @author Malikov Azizjon  *  15.01.2023  *  21:54   *  IbratClub
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VacancyDTO {

    private String name, description;
    private boolean active = true;
    private Long departmentId;

}
