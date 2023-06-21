package com.example.eventsystem.repository;

import com.example.eventsystem.model.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  13:36   *  tedaSystem
 */

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
    Page<Call> findAllByEmployee_IdAndClient_Department_Company_Id(Long employee_id, Long companyId, Pageable pageable);

    Page<Call> findAllByClient_Department_Company_Id(Long id, Pageable pageable);

    Page<Call> findAllByEmployee_IdAndClient_Department_Company_IdAndCreatedTimeBefore(Long employee_id,Long companyId, LocalDateTime createdTime, Pageable pageable);

    Page<Call> findAllByEmployee_IdAndClient_Department_Company_IdAndCreatedTimeAfter(Long employee_id, Long companyId, LocalDateTime createdTime, Pageable pageable);

    Page<Call> findAllByEmployee_IdAndClient_Department_Company_IdAndCreatedTimeBetween(Long employee_id, Long companyId,LocalDateTime createdTime, LocalDateTime createdTime2, Pageable pageable);

    Page<Call> findAllByClient_Id(Long clientId, Pageable pageable);
    @Query(value = "select count(*) from call where client_id = ?1", nativeQuery = true)
    int countByClient(Long clientId);

    @Query(value = "select * from call where call.client_id = ?1 order by created_time desc  limit 1", nativeQuery = true)
    Call getLastCallByClient(Long clientId);
}
