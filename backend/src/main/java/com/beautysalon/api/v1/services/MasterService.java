package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.*;
import com.beautysalon.api.v1.exceptions.ResourceAlreadyExists;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.api.v1.repository.AppointmentRepository;
import com.beautysalon.api.v1.repository.MasterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MasterService {
    private final MasterRepository masterRepository;
    private final AppointmentRepository appointmentRepository;
    private final ImageService imageService;

    public MasterService(
            MasterRepository masterRepository,
            AppointmentRepository appointmentRepository,
            ImageService imageService
    ) {
        this.masterRepository = masterRepository;
        this.appointmentRepository = appointmentRepository;
        this.imageService = imageService;
    }

    public Master create(Master master) {
        Master saved = masterRepository.save(master);
        return saved;
    }

    public void putImage(String username, Image image) {
        putImage(username, image, image.getName());
    }

    public void putImage(String username, Image image, String filename) {
        Master master = getByUsernameOrThrow(username);
        String fullName = String.format("emp/%s/%s", master.getId().toString(), filename);
        image.setName(fullName);
        master.getUser().getImages().add(image);
        masterRepository.save(master);
    }

    public List<Image> getImages(String username) {
        Master master = getByUsernameOrThrow(username);
        return imageService.getAll(String.format("emp/%s", master.getId().toString()));
    }

    public Optional<Master> getById(Long id) {
        return masterRepository.findById(id);
    }

    public Optional<Master> getByEmail(String email) {
        return masterRepository.findByEmail(email);
    }

    public Optional<Master> getByUsername(String username) {
        return masterRepository.findByUsername(username);
    }

    public List<Master> getAll() {
        return masterRepository.findAll();
    }

    public boolean isAvailableRangeDateTime(Long id, LocalDateTime start, LocalDateTime end) throws ResourceNotFoundException {
        Master master = getByIdOrThrow(id);
        LocalDate date = start.toLocalDate();

        boolean isWorkScheduleAvailable = master.getWorkSchedules().stream()
                .filter(x -> x.getDayOfWeek() == date.getDayOfWeek())
                .anyMatch(x -> isTimeRangeIntersecting(x.getStartTime(), x.getEndTime(), start.toLocalTime(), end.toLocalTime(), false));

        if (isWorkScheduleAvailable) {
            List<AvailabilitySchedule> availabilitySchedules = master.getAvailabilitySchedules();

            boolean isAvailabilityScheduleAvailable = availabilitySchedules.stream()
                    .filter(x -> x.getDate().equals(date))
                    .noneMatch(x -> isTimeRangeIntersecting(x.getStartTime(), x.getEndTime(), start.toLocalTime(), end.toLocalTime(), true)
                            && (x.getState() == ScheduleStatus.UNAVAILABLE || x.getState() == ScheduleStatus.MISSING));

            if (isAvailabilityScheduleAvailable) {
                List<Appointment> appointments = appointmentRepository.findAll();

                boolean isAppointmentAvailable = appointments.stream()
                        .filter(x -> x.getMaster().equals(master))
                        .noneMatch(x -> isAppointmentTimeConflict(x, start, end));

                return isAppointmentAvailable;
            }
        }
        return false;
    }

    private boolean isAppointmentTimeConflict(Appointment appointment, LocalDateTime newStart, LocalDateTime newEnd) {
        LocalDateTime existingEndDateTime = appointment.getAppointmentDate().plusMinutes(appointment.getTotalDurationInMinutes());
        LocalTime existingStartTime = appointment.getAppointmentDate().toLocalTime();
        LocalTime existingEndTime = existingEndDateTime.toLocalTime();

        boolean isSameDate = appointment.getAppointmentDate().toLocalDate().equals(newStart.toLocalDate());
        boolean isTimeConflict = isTimeRangeIntersecting(existingStartTime, existingEndTime, newStart.toLocalTime(), newEnd.toLocalTime(), true);

        return isSameDate && isTimeConflict;
    }

    private boolean isTimeRangeIntersecting(
            LocalTime workStartTime, LocalTime workEndTime,
            LocalTime appointmentStartTime, LocalTime appointmentEndTime,
            boolean partiality
    ) {
        boolean startInRange = appointmentStartTime.isAfter(workStartTime) && appointmentStartTime.isBefore(workEndTime);
        boolean endInRange = appointmentEndTime.isAfter(workStartTime) && appointmentEndTime.isBefore(workEndTime);
        return (partiality) ? startInRange || endInRange : startInRange && endInRange;
    }

    public void deleteByUsername(String username)
            throws EntityNotFoundException {
        Master master = getByUsernameOrThrow(username);
        masterRepository.deleteById(master.getId());
    }

    Master getByIdOrThrow(Long id) {
        return getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Master not found."));
    }
    Master getByUsernameOrThrow(String username) {
        return getByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("Master not found"));
    }
    Master getByEmailOrThrow(String email) {
        return getByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Master not found."));
    }

}
