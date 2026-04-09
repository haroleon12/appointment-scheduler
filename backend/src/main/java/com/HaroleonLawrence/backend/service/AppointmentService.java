package com.HaroleonLawrence.backend.service;


import com.HaroleonLawrence.backend.dto.AppointmentRequestDTO;
import com.HaroleonLawrence.backend.dto.AppointmentResponseDTO;
import com.HaroleonLawrence.backend.exception.DoubleBookingException;
import com.HaroleonLawrence.backend.exception.ResourceNotFoundException;
import com.HaroleonLawrence.backend.model.Appointment;
import com.HaroleonLawrence.backend.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + id));
        return toResponseDTO(appointment);
    }

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        repository.findByAppointmentDateTime(dto.getAppointmentDateTime())
                .ifPresent(existing -> {
                    throw new DoubleBookingException("This time slot is already booked");
                });

        Appointment appointment = new Appointment(
                dto.getCustomerName(),
                dto.getServiceType(),
                dto.getAppointmentDateTime(),
                dto.getStatus()
        );

        Appointment saved = repository.save(appointment);
        return toResponseDTO(saved);
    }

    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto) {
        Appointment existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + id));

        repository.findByAppointmentDateTime(dto.getAppointmentDateTime())
                .ifPresent(conflict -> {
                    if (!conflict.getId().equals(id)) {
                        throw new DoubleBookingException("This time slot is already booked");
                    }
                });

        existing.setCustomerName(dto.getCustomerName());
        existing.setServiceType(dto.getServiceType());
        existing.setAppointmentDateTime(dto.getAppointmentDateTime());
        existing.setStatus(dto.getStatus());

        Appointment saved = repository.save(existing);
        return toResponseDTO(saved);
    }

    public void deleteAppointment(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id " + id);
        }
        repository.deleteById(id);
    }

    private AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getCustomerName(),
                appointment.getServiceType(),
                appointment.getAppointmentDateTime(),
                appointment.getStatus()
        );
    }
}