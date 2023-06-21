package com.example.eventsystem.model;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class PrinterJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    @ManyToOne
    private Request request;
    private UUID qrcode;
    private int sequenceNumber;
}
