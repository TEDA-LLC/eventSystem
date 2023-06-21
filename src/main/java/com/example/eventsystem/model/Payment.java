package com.example.eventsystem.model;

import com.example.eventsystem.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Payment {//TODO tolovlar
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer paymentNumber;
    private LocalDate paymentDate;
    private Double price;
    private boolean active;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Order order;
    @ManyToOne
    private Employee receiver;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}
