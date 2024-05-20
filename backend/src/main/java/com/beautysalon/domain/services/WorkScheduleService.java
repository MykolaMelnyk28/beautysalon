package com.beautysalon.domain.services;

import com.beautysalon.api.v1.dto.mapper.WorkScheduleMapper;
import com.beautysalon.domain.repository.WorkScheduleRepository;
import com.beautysalon.domain.entities.WorkSchedule;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;

    public WorkScheduleService(WorkScheduleRepository workScheduleRepository) {
        this.workScheduleRepository = workScheduleRepository;
    }

    public WorkSchedule create(WorkSchedule workSchedule){
        return workScheduleRepository.save(workSchedule);
    }

    public List<WorkSchedule> getAll() {
        return workScheduleRepository.findAll();
    }

    public Optional<WorkSchedule> getById(Long id) {
        return workScheduleRepository.findById(id);
    }

    public Optional<WorkSchedule> getByPeriod(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return workScheduleRepository.findByPeriod(dayOfWeek, startTime, endTime);
    }

    public WorkSchedule updateById(Long id, WorkSchedule workSchedule) {
        WorkSchedule found = getByIdOrThrow(id);
        workSchedule.setStartTime(workSchedule.getStartTime());
        workSchedule.setEndTime(workSchedule.getEndTime());
        workSchedule.setDayOfWeek(workSchedule.getDayOfWeek());
        return workScheduleRepository.save(found);
    }

    public void deleteById(Long id) {
        getByIdOrThrow(id);
        workScheduleRepository.deleteById(id);
    }

    WorkSchedule getByPeriodOrThrow(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return getByPeriod(dayOfWeek, startTime, endTime).orElseThrow(() ->
                new ResourceNotFoundException("WorkSchedule not found."));
    }

    WorkSchedule getByIdOrThrow(Long id) {
        return getById(id).orElseThrow(() ->
                new ResourceNotFoundException("WorkSchedule not found"));
    }
}
