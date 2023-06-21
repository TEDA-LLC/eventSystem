package com.example.eventsystem.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Mansurov Abdusamad  *  01.12.2022  *  14:47   *  tedaSystem
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CallDTO {

    private Long clientId, requestId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate nextConnectionDate;

    private String description;

    private List<Long> reviewCategoryIds;

    boolean success = false;

}
