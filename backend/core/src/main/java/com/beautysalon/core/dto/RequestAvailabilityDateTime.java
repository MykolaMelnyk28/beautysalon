package com.beautysalon.core.dto;

import com.beautysalon.core.dto.validation.OnCreate;
import com.beautysalon.core.dto.validation.OnPatch;
import com.beautysalon.core.dto.validation.OnPut;
import com.beautysalon.core.dto.validation.StartBeforeEnd;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.Objects;

@StartBeforeEnd(message = "startDatetime must be before than endDatetime",
        groups = {OnCreate.class, OnPut.class, OnPatch.class})
public class RequestAvailabilityDateTime {

    @NotNull(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class, OnPatch.class})
    @Future(message = "data and time must be after current time",
            groups = {OnCreate.class, OnPut.class, OnPatch.class})
    private LocalDateTime startDatetime;

    @NotNull(message = "must not be empty")
    @Future(message = "data and time must be after current time",
            groups = {OnCreate.class, OnPut.class, OnPatch.class})
    private LocalDateTime endDatetime;

    public RequestAvailabilityDateTime() {
    }

    public RequestAvailabilityDateTime(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestAvailabilityDateTime that = (RequestAvailabilityDateTime) o;
        return Objects.equals(startDatetime, that.startDatetime) && Objects.equals(endDatetime, that.endDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDatetime, endDatetime);
    }
}
