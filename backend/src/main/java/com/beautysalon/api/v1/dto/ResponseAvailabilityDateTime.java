package com.beautysalon.api.v1.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ResponseAvailabilityDateTime {
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private boolean accessibility;

    public ResponseAvailabilityDateTime() {
    }

    public ResponseAvailabilityDateTime(LocalDateTime startDatetime, LocalDateTime endDatetime, boolean accessibility) {
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.accessibility = accessibility;
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

    public boolean isAccessibility() {
        return accessibility;
    }

    public void setAccessibility(boolean accessibility) {
        this.accessibility = accessibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseAvailabilityDateTime that = (ResponseAvailabilityDateTime) o;
        return accessibility == that.accessibility && Objects.equals(startDatetime, that.startDatetime) && Objects.equals(endDatetime, that.endDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDatetime, endDatetime, accessibility);
    }
}


