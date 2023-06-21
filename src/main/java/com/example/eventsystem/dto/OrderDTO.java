package com.example.eventsystem.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * @author Mansurov Abdusamad  *  14.12.2022  *  11:45   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private Long clientId;
    private Long employeeId, receiverId;
    private Long productId;
    private Double price;
    private String description, orderType;
    private LocalDate startDate;
    private LocalDate endTime;
}
