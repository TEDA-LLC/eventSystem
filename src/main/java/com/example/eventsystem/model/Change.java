package com.example.eventsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Change {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long columnId;

    @Column(columnDefinition = "text")
    private String columnName, tableName, oldData, newData;

    @ManyToOne
    private Employee employee;

    private LocalDateTime dateTime;
}
