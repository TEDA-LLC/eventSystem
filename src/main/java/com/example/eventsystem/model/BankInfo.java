package com.example.eventsystem.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
public class BankInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String branch;

    private Integer mfo;

    private Integer accountNumber;

    private String currency;

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
//    @JsonIgnore
    @ToString.Exclude
    private Company company;
}
