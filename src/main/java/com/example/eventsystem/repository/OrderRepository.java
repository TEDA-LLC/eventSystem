package com.example.eventsystem.repository;

import com.example.eventsystem.model.Employee;
import com.example.eventsystem.model.Order;
import com.example.eventsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  13:38   *  tedaSystem
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByClient_Department_Company_IdAndActiveTrueAndReadyTrue(Long companyId, Pageable pageable);

    Page<Order> findAllByClient_Department_Company_IdAndActiveTrueAndReadyFalse(Long companyId, Pageable pageable);

    Page<Order> findAllByClient_Department_Company_IdAndActiveFalseAndReadyTrue(Long companyId, Pageable pageable);

    Page<Order> findAllByClient_Department_Company_IdAndActiveFalseAndReadyFalse(Long companyId, Pageable pageable);

    List<Order> findAllByClient(User client);
    List<Order> findAllByEmployee(Employee employee);
    List<Order> findAllByReceiver(Employee receiver);


}
