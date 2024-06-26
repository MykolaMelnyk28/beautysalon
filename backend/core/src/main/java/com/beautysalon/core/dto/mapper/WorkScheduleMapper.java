package com.beautysalon.core.dto.mapper;

import com.beautysalon.core.dto.WorkScheduleDto;
import com.beautysalon.core.dto.mapper.base.AbstractMapper;
import com.beautysalon.core.entities.WorkSchedule;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class WorkScheduleMapper extends AbstractMapper<WorkSchedule, WorkScheduleDto> {
    @Override
    protected void copyDto(WorkSchedule source, WorkScheduleDto destination, boolean hardSet) {
//        String weekday = source.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("uk_UA"));
//        destination.setWeekday(weekday);

        destination.setWeekday(source.getDayOfWeek().ordinal() + 1);

        String startTime = source.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm"));
        String endTime = source.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm"));
        String time = String.format("%s-%s", startTime, endTime);
        destination.setTime(time);
    }

    @Override
    protected void copyEntity(WorkScheduleDto source, WorkSchedule destination, boolean hardSet) {
//        DayOfWeek.valueOf("");
//        DayOfWeek dayOfWeek = DayOfWeek.valueOf(source.getWeekday().toUpperCase());
//        destination.setDayOfWeek(dayOfWeek);

        destination.setDayOfWeek(DayOfWeek.of(source.getWeekday()));

        String[] timeParts = source.getTime().split("-");
        destination.setStartTime(LocalTime.parse(timeParts[0]));
        destination.setEndTime(LocalTime.parse(timeParts[1]));
    }
}

