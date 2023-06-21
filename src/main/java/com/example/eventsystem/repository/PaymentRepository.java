package com.example.eventsystem.repository;

import com.example.eventsystem.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  13:38   *  tedaSystem
 */

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Page<Payment> findAllByReceiver_Company_Id(Long companyId, Pageable pageable);
}
