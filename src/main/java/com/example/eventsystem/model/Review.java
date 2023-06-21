package com.example.eventsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Mansurov Abdusamad  *  05.12.2022  *  12:19   *  tedaUz
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Review", indexes = {
        @Index(name = "idx_review_active", columnList = "confirmation")
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime = LocalDateTime.now();
    private boolean confirmation = false;

}
