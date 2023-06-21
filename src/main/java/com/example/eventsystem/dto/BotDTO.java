package com.example.eventsystem.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotDTO {
    private String token, username;
    private Long departmentId;
    private boolean active;

    private MultipartFile photo;
}
