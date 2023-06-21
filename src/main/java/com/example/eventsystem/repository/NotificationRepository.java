package com.example.eventsystem.repository;

import com.example.eventsystem.model.Company;
import com.example.eventsystem.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findAllByViewFalseAndCompany(Company company, Pageable pageable);
}
