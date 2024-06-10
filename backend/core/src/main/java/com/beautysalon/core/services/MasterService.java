package com.beautysalon.core.services;

import com.beautysalon.core.exceptions.ResourceNotFoundException;
import com.beautysalon.core.exceptions.StorageException;
import com.beautysalon.core.repository.AppointmentRepository;
import com.beautysalon.core.repository.ImageRepository;
import com.beautysalon.core.repository.MasterRepository;
import com.beautysalon.core.utils.PathUtils;
import com.beautysalon.core.entities.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MasterService {
    private final MasterRepository masterRepository;
    private final AppointmentRepository appointmentRepository;
    private final ImageRepository imageRepository;
    private final ImageServiceImpl imageService;


    public MasterService(
            MasterRepository masterRepository,
            AppointmentRepository appointmentRepository,
            ImageRepository imageRepository,
            ImageServiceImpl imageService
    ) {
        this.masterRepository = masterRepository;
        this.appointmentRepository = appointmentRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }

    public Master create(Master master) {
        Master saved = masterRepository.save(master);
        return saved;
    }

    public Image putPreviewImage(String username, Image image) {
        String path = PathUtils.parseEmployeeImagePath(username, image.getName());
        if (imageService.exists(path)) {
            imageService.delete(path);
        }
        image.setPreviewImage(true);
        return putImage(username, image, image.getName());
    }
    public Image putImage(String username, Image image) {
        return putImage(username, image, image.getName());
    }

    public Image putImage(String username, Image image, String filename) {
        try {
            String oPath = PathUtils.originalPath(filename);
            Master master = getByUsernameOrThrow(username);
            String fullName = PathUtils.parseEmployeeImagePath(username, oPath);
            image.setUser(master.getUser());
            return imageService.store(image, fullName);
        } catch (Exception e) {
            throw new StorageException("Error store image for employee.");
        }
    }

    public List<Image> getImages(String username) {
        getByUsernameOrThrow(username);
        return imageService.getAll(PathUtils.parseEmployeeImagePath(username, ""));
    }

    public void deleteImage(String username, String filename) {
        getByUsernameOrThrow(username);
        imageService.delete(PathUtils.parseEmployeeImagePath(username, filename));
    }

    public Optional<Image> getPreviewImage(String username) {
        return imageRepository.findUserPreviewImage(username);
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

    @Transactional(readOnly = true)
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
