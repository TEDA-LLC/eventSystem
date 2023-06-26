package com.example.eventsystem.repository;

import com.example.eventsystem.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    @Query(value = "select count(id) as amount from user_history where product_id =:id",nativeQuery = true)
    long getAmountByProduct(@Param("id") long id);

    List<UserHistory> findAllByUser_Department_Company_Id(Long companyId);
}
