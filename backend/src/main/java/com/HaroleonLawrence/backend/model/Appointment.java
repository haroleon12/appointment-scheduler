package com.HaroleonLawrence.backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "appointments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "appointment_datetime")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String serviceType;

    @Column(name = "appointment_datetime", nullable = false)
    private LocalDateTime appointmentDateTime;

    @Column(nullable = false)
    private String status;

    public Appointment(String customerName, String serviceType, LocalDateTime appointmentDateTime, String status) {
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
    }
}

