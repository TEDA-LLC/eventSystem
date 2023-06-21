package com.example.eventsystem.repository;

import com.example.eventsystem.model.Bot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotRepository extends JpaRepository<Bot, Long> {

}
