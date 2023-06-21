package com.example.eventsystem.repository;

import com.example.eventsystem.model.Product;
import com.example.eventsystem.model.Request;
import com.example.eventsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mansurov Abdusamad  *  24.11.2022  *  10:30   *  tedaUz
 */

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Page<Request> findAllByViewAndUser_Department_Company_Id(boolean view,Long companyId, Pageable pageable);
    List<Request> findAllByProductAndUser(Product product, User user);
    @Query(value = "select count(*) from request where user_id = ?1", nativeQuery = true)
    int countByUser(Long userId);
}
