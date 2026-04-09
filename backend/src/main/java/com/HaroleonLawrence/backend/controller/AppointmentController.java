package com.HaroleonLawrence.backend.controller;

import com.HaroleonLawrence.backend.dto.AppointmentRequestDTO;
import com.HaroleonLawrence.backend.dto.AppointmentResponseDTO;
import com.HaroleonLawrence.backend.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return service.getAllAppointments();
    }

    @GetMapping("/{id}")
    public AppointmentResponseDTO getAppointmentById(@PathVariable Long id) {
        return service.getAppointmentById(id);
    }

    @PostMapping
    public AppointmentResponseDTO createAppointment(@Valid @RequestBody AppointmentRequestDTO dto) {
        return service.createAppointment(dto);
    }

    @PutMapping("/{id}")
    public AppointmentResponseDTO updateAppointment(@PathVariable Long id,
                                                    @Valid @RequestBody AppointmentRequestDTO dto) {
        return service.updateAppointment(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        service.deleteAppointment(id);
    }
}
