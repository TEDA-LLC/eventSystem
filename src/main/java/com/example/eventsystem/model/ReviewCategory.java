package com.example.eventsystem.model;

import com.example.eventsystem.model.enums.ActiveType;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:56   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class ReviewCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private ActiveType activeType;

    private boolean active = true;

}
