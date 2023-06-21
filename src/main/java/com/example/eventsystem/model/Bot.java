package com.example.eventsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Bot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String token, username;

//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Department department;
    @OneToMany
    @ToString.Exclude
    private List<Employee> authorizedEmployees;
//    @OneToMany(mappedBy = "bot")
//    @ToString.Exclude
//    private List<User> userList;
//    @OneToMany
//    @ToString.Exclude
//    private List<Category> categories;
//
//    @OneToMany(mappedBy = "bot")
//    @ToString.Exclude
//    private List<Vacancy> vacancies;
    @Column(nullable = false)
    private boolean active = true;
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Attachment logo;
}
