package com.example.eventsystem.repository;

import com.example.eventsystem.model.District;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mansurov Abdusamad  *  30.11.2022  *  10:08   *  tedaSystem
 */
@Component
@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {
    Optional<District> findByName(String name);
    List<District> findAllByRegion_Id(Long regionId,Sort sort);
}
