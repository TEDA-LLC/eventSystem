package com.example.eventsystem.repository;

import com.example.eventsystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author * Sunnatullayev Mahmudnazar *  * tedaUz *  * 10:45 *
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByDepartment_Id(Long departmentId);
}
