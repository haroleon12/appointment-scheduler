import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Appointment } from './appointment';
import { AppointmentService } from './appointment-service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {
  appointments: Appointment[] = [];
  errorMessage = '';

  newAppointment: Appointment = {
    customerName: '',
    serviceType: '',
    appointmentDateTime: '',
    status: 'BOOKED',
  };

  constructor(private appointmentService: AppointmentService) {}

  ngOnInit(): void {
    this.loadAppointments();
  }

  loadAppointments(): void {
    this.appointmentService.getAppointments().subscribe({
      next: (data) => {
        this.appointments = data;
      },
      error: () => {
        this.errorMessage = 'Could not load appointments.';
      },
    });
  }

  addAppointment(): void {
    this.errorMessage = '';

    this.appointmentService.addAppointment(this.newAppointment).subscribe({
      next: () => {
        this.newAppointment = {
          customerName: '',
          serviceType: '',
          appointmentDateTime: '',
          status: 'BOOKED',
        };
        this.loadAppointments();
      },
      error: (err) => {
        if (err?.error?.validationErrors) {
          this.errorMessage = Object.values(err.error.validationErrors).join(' | ');
        } else {
          this.errorMessage = err?.error?.message || 'Could not create appointment.';
        }
      },
    });
  }

  deleteAppointment(id: number): void {
    this.errorMessage = '';

    this.appointmentService.deleteAppointment(id).subscribe({
      next: () => {
        this.loadAppointments();
      },
      error: () => {
        this.errorMessage = 'Could not delete appointment.';
      },
    });
  }
}
