package com.beautysalon.api.v1.dto.mapper;

import com.beautysalon.api.v1.dto.WorkScheduleDto;
import com.beautysalon.api.v1.dto.mapper.base.AbstractMapper;
import com.beautysalon.api.v1.entities.WorkSchedule;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Component
public class WorkScheduleMapper extends AbstractMapper<WorkSchedule, WorkScheduleDto> {
    @Override
    protected void copyDto(WorkSchedule source, WorkScheduleDto destination) {
//        String weekday = source.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("uk_UA"));
//        destination.setWeekday(weekday);

        destination.setWeekday(source.getDayOfWeek().ordinal() + 1);

        String startTime = source.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm"));
        String endTime = source.getEndTime().format(DateTimeFormatter.ofPattern("hh:mm"));
        String time = String.format("%s-%s", startTime, endTime);
        destination.setTime(time);
    }

    @Override
    protected void copyEntity(WorkScheduleDto source, WorkSchedule destination) {
//        DayOfWeek.valueOf("");
//        DayOfWeek dayOfWeek = DayOfWeek.valueOf(source.getWeekday().toUpperCase());
//        destination.setDayOfWeek(dayOfWeek);

        destination.setDayOfWeek(DayOfWeek.of(source.getWeekday()));

        String[] timeParts = source.getTime().split("-");
        destination.setStartTime(LocalTime.parse(timeParts[0]));
        destination.setEndTime(LocalTime.parse(timeParts[1]));
    }
}

