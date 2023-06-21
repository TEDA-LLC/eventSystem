package com.example.eventsystem.repository;

import com.example.eventsystem.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    List<Site> findAllByDepartment_Company_Id(Long companyId);
}
