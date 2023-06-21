package com.example.eventsystem.repository;

import com.example.eventsystem.model.SiteHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author * Sunnatullayev Mahmudnazar *  * tedabot *  * 15:33 *
 */
@Repository
public interface SiteHistoryRepository extends JpaRepository<SiteHistory,Long> {
}
