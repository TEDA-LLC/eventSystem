package com.example.eventsystem.model;

import com.example.eventsystem.model.enums.ActiveType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ActiveType activityType;
    private String stirNumber;
    @ManyToOne
    private Company memberOrganization;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @JsonIgnore
    private List<BankInfo> bankInfo;
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Employee director;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registeredTime = LocalDateTime.now();
    //    @ManyToMany(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private List<User> clientList;
//    @OneToMany(mappedBy = "company")
//    @ToString.Exclude
//    private List<Bot> botList;
//    @OneToMany(mappedBy = "company")
//    @ToString.Exclude
//    private List<Site> siteList;
    @ManyToMany()
    @ToString.Exclude
    @JsonIgnore
    private List<Department> departmentList;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Employee> employees;
    @Builder.Default
    private boolean active = true;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment attachment;
    private Double abortion;
    private String INN;
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
//    @JsonIgnore
    private List<WorkCategory> workCategoryList;

    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
//    @JsonIgnore
    private List<WorkType> workTypeList;

    public Company(Employee director) {
        this.director = director;
    }


    public Company(String name, ActiveType activityType, String stirNumber, Address address, Company memberOrganization, BankInfo bankInfo, Employee director) {
        this.name = name;
        this.activityType = activityType;
        this.stirNumber = stirNumber;
        this.address = address;
        this.memberOrganization = memberOrganization;
//        this.bankInfo = bankInfo;
        this.director = director;
    }
}