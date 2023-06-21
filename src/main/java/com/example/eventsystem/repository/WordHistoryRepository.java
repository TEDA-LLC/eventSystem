package com.example.eventsystem.repository;

import com.example.eventsystem.model.WordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author * Sunnatullayev Mahmudnazar *  * tedabot *  * 10:29 *
 */
@Repository
public interface WordHistoryRepository extends JpaRepository<WordHistory,Long> {
    List<WordHistory> findAllByUser_Department_Company_Id(Long companyId);
}
