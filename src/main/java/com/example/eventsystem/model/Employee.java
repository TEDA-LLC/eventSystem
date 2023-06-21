package com.example.eventsystem.model;

import com.example.eventsystem.model.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:35   *  tedaSystem
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username, fullName;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String phoneFirst;

    private String phoneSecond;
    @JsonIgnore
    private String chatId;

    @ManyToOne
//    @JsonIgnore
    @ToString.Exclude
    private Company company;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    private boolean active = true;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registeredTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<RoleType> roles;
//    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private RoleType selectedRole;

    @ManyToOne
    private Product product;

//        @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(new SimpleGrantedAuthority(selectedRole.name()));
//    }
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles
            .stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
            .collect(Collectors.toList());
}
    @JsonIgnore
    private boolean accountNonExpired = true, accountNonLocked = true, credentialsNonExpired = true;
    private boolean enabled = true;

    public Employee(String Username, String password, Set<RoleType> roles) {
        this.username = Username;
        this.password = password;
        this.roles = roles;
    }

//    public Employee(String username, String password, Set<RoleType> roles, RoleType selectedRole) {
//        this.username = username;
//        this.password = password;
//        this.roles = roles;
//        this.selectedRole = selectedRole;
//    }

    public Employee(String username, String password, Set<RoleType> roles, String phoneFirst) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.phoneFirst = phoneFirst;
    }
//    public Employee(String username, String password, Set<RoleType> roles, RoleType selectedRole, String phoneFirst) {
//        this.username = username;
//        this.password = password;
//        this.roles = roles;
//        this.selectedRole = selectedRole;
//        this.phoneFirst = phoneFirst;
//    }
}
