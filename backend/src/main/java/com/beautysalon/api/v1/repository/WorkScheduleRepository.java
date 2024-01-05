package com.beautysalon.api.v1.repository;

import com.beautysalon.api.v1.entities.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {

    @Query("""
        SELECT ws FROM WorkSchedule ws
        WHERE ws.dayOfWeek = :dayOfWeek AND 
            ws.startTime = :startTime AND 
            ws.endTime = :endTime
        """)
    Optional<WorkSchedule> findByPeriod(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime);
}
