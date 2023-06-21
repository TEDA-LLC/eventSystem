package com.example.eventsystem.dto;

import com.example.eventsystem.model.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MessageResponseDTO {
    private  Long id;
    private String text;
    private String phone;
    private MessageType messageType;
}
