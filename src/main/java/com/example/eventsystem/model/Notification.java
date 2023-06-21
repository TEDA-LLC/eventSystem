package com.example.eventsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne
    private Employee employee;
    private LocalDateTime dateTime = LocalDateTime.now();
    private String beforeUser;
    private boolean view = false;
    @ManyToOne
    private Company company;
}
