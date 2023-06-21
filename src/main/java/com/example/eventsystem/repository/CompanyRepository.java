package com.example.eventsystem.repository;

import com.example.eventsystem.model.Company;
import com.example.eventsystem.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Malikov Azizjon    ourSystem    26.12.2022    15:40
 */

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByINN(String inn);
    List<Company> findAllByDirector(Employee director);

    Page<Company> findAllByActive(Pageable pageable, boolean active);
    Page<Company> findAllByActiveAndNameLikeIgnoreCase(boolean active, String name, Pageable pageable);

}
