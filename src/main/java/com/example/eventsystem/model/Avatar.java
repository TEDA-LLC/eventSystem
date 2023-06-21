package com.example.eventsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Mansurov Abdusamad  *  01.12.2022  *  10:08   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Attachment> photos;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @ToString.Exclude
    private User user;
    @Column(columnDefinition = "text")
    private String personal, aboutWork, hobby;
}
