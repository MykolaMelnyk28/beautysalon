package com.beautysalon.ui.mapper;

import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.domain.entities.WorkSchedule;
import com.beautysalon.ui.controller.WorkScheduleModel;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class WorkScheduleModelMapper extends AbstractMapper<WorkSchedule, WorkScheduleModel> {
    @Override
    protected void postEntityCopy(WorkScheduleModel source, WorkSchedule destination) {
        destination.setDayOfWeek(DayOfWeek.of(source.getWeekday()));
    }

    @Override
    protected void postDtoCopy(WorkSchedule source, WorkScheduleModel destination) {
        destination.setWeekday(source.getDayOfWeek().getValue());
    }
}
