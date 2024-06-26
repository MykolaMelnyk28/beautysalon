package com.beautysalon.core.repository;

import com.beautysalon.core.entities.EmployeePosition;
import com.beautysalon.core.entities.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    Optional<Master> findByEmail(String email);

    @Query("""
    SELECT m FROM Master m WHERE m.user.username = :username
    """)
    Optional<Master> findByUsername(@Param("username") String username);
    List<Master> findAllByPosition(EmployeePosition position);
}
