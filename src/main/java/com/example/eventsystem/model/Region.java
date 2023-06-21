package com.example.eventsystem.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Country country;

    public Region(String name, String description, Country country) {
        this.name = name;
        this.description = description;
        this.country = country;
    }
}