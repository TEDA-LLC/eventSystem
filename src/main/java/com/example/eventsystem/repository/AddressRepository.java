package com.example.eventsystem.repository;

import com.example.eventsystem.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author * Sunnatullayev Mahmudnazar *  * tedaUz *  * 10:47 *
 */
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}