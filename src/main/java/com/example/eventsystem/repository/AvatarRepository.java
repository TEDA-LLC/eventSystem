package com.example.eventsystem.repository;

import com.example.eventsystem.model.Avatar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mansurov Abdusamad  *  07.12.2022  *  12:12   *  tedaSystem
 */

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Page<Avatar> findAllByUser_Department_Company_Id(Long companyId, Pageable pageable);
}
