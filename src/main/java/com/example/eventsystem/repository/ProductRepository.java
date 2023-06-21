package com.example.eventsystem.repository;

import com.example.eventsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author * Sunnatullayev Mahmudnazar *  * tedabot *  * 16:24 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategoryId(Long id);
    List<Product> findAllByActiveTrueAndCategory_Department_Company_Id(Long botId);

    Optional<Product> findByIdAndCategory_Department_Company_Id(Long eventId,Long companyId);
}
