package com.example.eventsystem.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO {
    private String username, password;
}
