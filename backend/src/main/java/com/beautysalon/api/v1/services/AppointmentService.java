package com.beautysalon.api.v1.services;

import com.beautysalon.api.v1.entities.*;
import com.beautysalon.api.v1.exceptions.ResourceNotFoundException;
import com.beautysalon.api.v1.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

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
        appointment.setStatus(AppointmentStatus.NEW);
        Appointment saved = appointmentRepository.save(appointment);
        // TODO: Send massage to client email
        // TODO: Send message to master email
        return saved;
    }

    public Appointment assignClient(Appointment appointment) {
        if (appointment.getClient() != null && appointment.getClient().getEmail() != null) {
            Optional<Client> clientOpt = clientService.getByEmail(appointment.getClient().getEmail());
            clientOpt.ifPresent(appointment::setClient);
        }
        return appointment;
    }

    public Appointment assignMaster(Appointment appointment) {
        Master master = appointment.getMaster();
        if (master != null) {
            if (master.getId() != null) {
                appointment.setMaster(masterService.getByIdOrThrow(master.getId()));
            } else if(master.getEmail() != null) {
                appointment.setMaster(masterService.getByEmailOrThrow(master.getEmail()));
            } else if (master.getUser() != null && master.getUser().getUsername() != null) {
                appointment.setMaster(masterService.getByUsernameOrThrow(master.getUser().getUsername()));
            } else {
                throw new ResourceNotFoundException("Master entity from specify Appointment object is not found.");
            }
        }
        return appointment;
    }

    public Appointment assignServices(Appointment appointment) {
        if (appointment.getServices() != null && !appointment.getServices().isEmpty()) {
            appointment.setServices(appointment.getServices().stream()
                    .map(x -> (x.getId() == null) ? x : serviceEntityService.getByIdOrThrow(x.getId()))
                    .toList());
        }
        return appointment;
    }

    public Appointment update(Long id, Appointment appointment) {
        Appointment found = getByIdOrThrow(id);
        found.setMaster(appointment.getMaster());
        found.setClient(appointment.getClient());
        found.setServices(appointment.getServices());
        assignServices( assignClient( assignMaster(found) ) );
        Appointment updated = appointmentRepository.save(appointment);
        // TODO: Send massage to client email
        // TODO: Send message to master email
        return updated;
    }

    public void setStatus(Long id, AppointmentStatus status) {
        setStatus(id, status, "");
    }

    public void setStatus(Long id, AppointmentStatus status, String comment) {
        Appointment found = getByIdOrThrow(id);
        found.setStatus(status);
        appointmentRepository.save(found);
        // TODO: Send massage to client email
        // TODO: Send message to master email
    }

    public Optional<Appointment> getById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
        // TODO: Send massage to client email
        // TODO: Send message to master email
    }
    public Appointment getByIdOrThrow(Long id) throws ResourceNotFoundException {
        return getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Appointment not found."));
    }

}
