package com.example.eventsystem.repository;

import com.example.eventsystem.model.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Long> {
    Optional<WorkType> findByName(String name);
}
