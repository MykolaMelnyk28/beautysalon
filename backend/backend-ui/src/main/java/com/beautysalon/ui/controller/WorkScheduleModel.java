package com.beautysalon.ui.controller;

import com.beautysalon.api.v1.dto.validation.OnCreate;
import com.beautysalon.api.v1.dto.validation.OnPut;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class WorkScheduleModel {
        private Long id;

        private int weekday;

        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime startTime;

        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime endTime;


        public WorkScheduleModel() {
        }

        public WorkScheduleModel(Long id, int weekday, LocalTime startTime, LocalTime endTime) {
            this.id = id;
            this.weekday = weekday;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getWeekday() {
            return weekday;
        }

        public String toShortFormat() {
            StringBuilder builder = new StringBuilder();
            DayOfWeek dow = DayOfWeek.of(weekday);
            builder.append(dow.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("uk-UA")));
            builder.append(' ');
            builder.append(startTime.format(DateTimeFormatter.ofPattern("hh:mm")));
            builder.append("-");
            builder.append(endTime.format(DateTimeFormatter.ofPattern("hh:mm")));
            return builder.toString();
        }

        public void setWeekday(int weekday) {
            this.weekday = weekday;
        }
        public LocalTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WorkScheduleModel that = (WorkScheduleModel) o;
            return weekday == that.weekday && Objects.equals(id, that.id) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, weekday, startTime, endTime);
        }
    }