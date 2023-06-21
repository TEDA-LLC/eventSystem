package com.example.eventsystem.model;

import com.example.eventsystem.model.enums.MessageType;
import com.example.eventsystem.model.enums.SendType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String text;

    @ManyToOne
    private Bot bot;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne
    private Request request;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Enumerated(EnumType.STRING)
    private SendType sendType;

    private LocalDateTime sendTime;

    private LocalDateTime acceptTime;

    private boolean accept;

    private String email;

    @ManyToOne
    private Employee employee;
}
