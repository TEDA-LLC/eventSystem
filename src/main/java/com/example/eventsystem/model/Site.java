package com.example.eventsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name, link;
    @Column(columnDefinition = "text")
    private String description;

    private LocalDateTime dateTime = LocalDateTime.now();

    //    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Department department;

    @Column(nullable = true)
    private boolean active = true;
}
