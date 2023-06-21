package com.example.eventsystem.model;

import com.example.eventsystem.model.enums.Gender;
import com.example.eventsystem.model.enums.Language;
import com.example.eventsystem.model.enums.RegisteredType;
import com.example.eventsystem.model.enums.State;
import com.example.eventsystem.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:08   *  tedaSystem
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username, fullName;
    //    @Column(unique = true)
    private String phone, email;
    //    @Column(unique = true)
    @JsonIgnore
    private String chatId;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private State state;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String passportNumber;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Language language;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Avatar avatar;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Department department;
    private int count = 0;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate brithDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registeredTime = LocalDateTime.now();
    @JsonIgnore
    private LocalDateTime lastOperationTime;
    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<ActivityStatus> statusList;
    private boolean active = true;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private RegisteredType registeredType;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(unique = true)
    private UUID qrcode = UUID.randomUUID();
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = true)
    private boolean resident = true;
    private String know, company, workType;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
    @ManyToOne
    @ToString.Exclude
    private Employee employee;
    private LocalDateTime botRegisteredTime;
    private LocalDateTime siteRegisteredTime;
}
