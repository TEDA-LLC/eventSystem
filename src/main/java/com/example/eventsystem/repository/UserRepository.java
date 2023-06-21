package com.example.eventsystem.repository;

import com.example.eventsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author * Sunnatullayev Mahmudnazar *  * tedaUz *  * 11:02 *
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    List<User> findAllByDepartment_Id(Long departmentId);
    List<User> findAllByEmployee_Id(Long id);
    List<User> findAllByDepartment_Company_IdAndEmployee_Id(Long companyId, Long employeeId);
    Optional<User> findByQrcode( UUID qrcode);
    Optional<User> findByPhone(String phone);
    Optional<User> findByPhoneAndDepartment_Id(String phone, Long departmentId);
    Optional<User> findByPhoneAndDepartment_Company_Id(String phone, Long companyId);
    Optional<User> findByEmailAndDepartment_Id(String email, Long departmentId);
//    Optional<User> findByEmailAndDepartment_Company_Id(String email, Long companyId);
//    Optional<User> findByEmailAndPhoneAndDepartment_Company_Id(String email, String phone, Long companyId);
    Page<User> findAllByDepartment_Company_Id(Long companyId, Pageable pageable);
    Page<User> findAllByActiveTrueAndDepartment_Company_Id(Long companyId, Pageable pageable);
    Page<User> findAllByActiveFalseAndDepartment_Company_Id(Long companyId, Pageable pageable);
//    Page<User> findAllByActiveTrueAnd(Pageable pageable);

    Optional<User> findByChatId(String chatId);

    @Query(nativeQuery = true, value = "select * from users inner join users_roles on users.id = users_roles.users_id where users_roles.roles = :role")
    User getByRole(String role);


}
