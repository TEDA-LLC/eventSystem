package com.example.eventsystem.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * @author Mansurov Abdusamad  *  13.12.2022  *  16:17   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {
    private Integer paymentNumber;
    private LocalDate paymentDate;
    private Double price;
    private boolean active;
    private Long orderId;
    private String paymentType;
}
