package com.example.eventsystem.repository;

import com.example.eventsystem.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Malikov Azizjon  *  15.01.2023  *  21:50   *  IbratClub
 */
@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    //    List<Vacancy> findAllByVacancyId(Long id);
    List<Vacancy> findAllByActiveTrueAndDepartment_Company_Id(Long companyId);

}
