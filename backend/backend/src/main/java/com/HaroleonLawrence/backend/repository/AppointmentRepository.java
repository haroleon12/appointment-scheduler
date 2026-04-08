package com.HaroleonLawrence.backend.repository;



import com.HaroleonLawrence.backend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByAppointmentDateTime(LocalDateTime appointmentDateTime);
}