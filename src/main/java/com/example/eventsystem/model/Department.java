package com.example.eventsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Bot bot;

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Site site;

    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    @JsonIgnore
    private List<User> clientList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
//    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    private List<Category> categories;

    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    private List<Vacancy> vacancies;

    @Column(nullable = true)
    private boolean active = true;
}
