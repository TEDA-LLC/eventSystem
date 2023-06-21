package com.example.eventsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:08   *  tedaSystem
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class WordHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime = LocalDateTime.now();
    private String word;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Department department;
}
