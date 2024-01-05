package com.beautysalon.api.v1.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class RequestAvailabilityDateTime {
    LocalDateTime startDatetime;
    LocalDateTime endDatetime;

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
