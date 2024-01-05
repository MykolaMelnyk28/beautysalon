package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.WorkSchedule;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.api.v1.repository.WorkScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;

    public WorkScheduleService(WorkScheduleRepository workScheduleRepository) {
        this.workScheduleRepository = workScheduleRepository;
    }

    public Optional<WorkSchedule> getByPeriod(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return workScheduleRepository.findByPeriod(dayOfWeek, startTime, endTime);
    }

    WorkSchedule getByPeriodOrThrow(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return getByPeriod(dayOfWeek, startTime, endTime).orElseThrow(() ->
                new ResourceNotFoundException("WorkSchedule not found."));
    }
}
