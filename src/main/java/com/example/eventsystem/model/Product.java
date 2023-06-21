package com.example.eventsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "text")
    private String nameUz, nameRu, nameEn, descriptionUz, descriptionRu, descriptionEn, textUz, textRu, textEn, agreeTextUz, agreeTextEn, agreeTextRu;
    @ManyToOne
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment attachment;

    private Double price;
    private LocalDateTime fromDate, toDate;
    private Integer minimumTerm;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    private String executionInterval;

    @Builder.Default
    private boolean active = true;
    @OneToMany
    @ToString.Exclude
    private List<User> speakers;
}
