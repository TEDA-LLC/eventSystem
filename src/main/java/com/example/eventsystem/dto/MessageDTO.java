package com.example.eventsystem.dto;

import com.example.eventsystem.model.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MessageDTO {

    private String text;
    private Long user_id;
    private Long request_id;
    private MessageType messageType;
    private LocalDateTime sendTime;
    public MessageDTO(String text, Long user_id, Long request_id, MessageType messageType) {
        this.text = text;
        this.user_id = user_id;
        this.request_id = request_id;
        this.messageType = messageType;
        this.sendTime = LocalDateTime.now();
    }
}
