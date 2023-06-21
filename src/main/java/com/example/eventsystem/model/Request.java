package com.example.eventsystem.model;

import com.example.eventsystem.model.enums.RegisteredType;
import com.example.eventsystem.model.enums.RequestType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:08   *  tedaSystem
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String aboutProduct, category;
    private boolean view = false;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime = LocalDateTime.now();
    @ManyToOne
    private Product product;
    @Enumerated(EnumType.STRING)
    private RequestType requestStatusType;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @Enumerated(EnumType.STRING)
    private RegisteredType registeredType;
    @ManyToOne
    private Employee employee;
    private LocalDateTime arrivalTime;
    private boolean agree;
}
