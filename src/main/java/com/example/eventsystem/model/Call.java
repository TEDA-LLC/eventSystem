package com.example.eventsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:23   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User client;

    @ManyToOne
    private Employee employee;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime = LocalDateTime.now();

    private LocalDate nextConnectionDate;

    private String description;

    @OneToMany
    @ToString.Exclude
    private List<ReviewCategory> ReviewCategory;

    private boolean success;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Department department;
}
