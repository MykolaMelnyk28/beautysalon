package com.beautysalon.api.v1.dto;

import com.beautysalon.api.v1.dto.validation.OnAlways;
import com.beautysalon.api.v1.dto.validation.OnCreate;
import com.beautysalon.api.v1.dto.validation.OnPut;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

public class WorkScheduleDto {

    @Range(min = 1, max = 7,
            message = "must be in range 1 to 7")
    private int weekday;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class})
    @Pattern(regexp = "^\\d\\d:\\d\\d-\\d\\d:\\d\\d$",
            message = "invalid time string format",
            groups = {OnAlways.class})
    private String time;

    public WorkScheduleDto() {
    }

    public WorkScheduleDto(int weekday, String time) {
        this.weekday = weekday;
        this.time = time;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkScheduleDto that = (WorkScheduleDto) o;
        return weekday == that.weekday && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weekday, time);
    }
}
