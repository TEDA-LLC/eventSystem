package com.example.eventsystem.model;

import com.example.eventsystem.model.enums.ActiveType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author Mansurov Abdusamad  *  15.12.2022  *  10:29   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ActivityStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ActiveType firstCase;
    private ActiveType secondCase;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime period = LocalDateTime.now();
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private User client;
}
