package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.Appointment;
import com.beautysalon.api.v1.entities.Client;
import com.beautysalon.api.v1.entities.Master;
import com.beautysalon.api.v1.entities.ServiceEntity;
import com.beautysalon.api.v1.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClientService clientService;
    private final MasterService masterService;
    private final ServiceEntityService serviceEntityService;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            ClientService clientService,
            MasterService masterService,
            ServiceEntityService serviceEntityService
    ) {
        this.appointmentRepository = appointmentRepository;
        this.clientService = clientService;
        this.masterService = masterService;
        this.serviceEntityService = serviceEntityService;
    }

    public Appointment create(Appointment appointment) {
        assignClient(appointment);
        assignMaster(appointment);
        assignServices(appointment);
        appointment.setAppointmentDate(LocalDateTime.now());


        Appointment saved = appointmentRepository.save(appointment);
        return saved;
    }

    public void assignClient(Appointment appointment) {
        if (appointment.getClient() != null && appointment.getClient().getEmail() != null) {
            Optional<Client> clientOpt = clientService.getByEmail(appointment.getClient().getEmail());
            clientOpt.ifPresent(appointment::setClient);
        }
    }

    public void assignMaster(Appointment appointment) {
        if (appointment.getMaster() != null) {
            Master master = null;
            if (appointment.getMaster().getId() != null) {
                master = masterService.getByIdOrThrow(appointment.getMaster().getId());
            } else if (appointment.getMaster().getEmail() != null) {
                master = masterService.getByEmailOrThrow(appointment.getMaster().getEmail());
            }
            appointment.setMaster(master);
        }
    }

    public void assignServices(Appointment appointment) {
        if (appointment.getServices() != null && !appointment.getServices().isEmpty()) {
            List<ServiceEntity> services = new ArrayList<>();
            appointment.getServices().forEach(x -> {
                ServiceEntity service = serviceEntityService.getByIdOrThrow(x.getId());
                services.add(service);
            });
            appointment.setServices(services);
        }
    }

    public Optional<Appointment> getById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
