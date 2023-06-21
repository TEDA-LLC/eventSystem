package com.example.eventsystem.repository;

import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Message;
import com.example.eventsystem.model.enums.MessageType;
import com.example.eventsystem.model.enums.SendType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findAllByEmployeeAndSendTime(Employee employee, LocalDateTime time, Pageable pageable);
    Page<Message> findAllByEmployeeAndSendTimeAndMessageType(Employee employee, LocalDateTime sendTime, MessageType messageType, Pageable pageable);
    Page<Message> findAllByEmployeeAndSendTimeNullAndMessageTypeNotNullAndSendType(Employee employee, SendType sendType, Pageable pageable);
    Page<Message> findAllByEmployee(Employee employee, Pageable pageable);
}