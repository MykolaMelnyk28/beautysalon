package com.beautysalon.api.v1.dto;

import java.util.Objects;

public class WorkScheduleDto {
    private int weekday;
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
