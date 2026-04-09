package com.HaroleonLawrence.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private String customerName;
    private String serviceType;
    private LocalDateTime appointmentDateTime;
    private String status;
}
