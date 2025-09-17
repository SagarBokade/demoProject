package com.cognizant.hams.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "doctor_availability")
@Data
public class DoctorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availabilityId;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;
    private LocalDate date;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isBooked;
}
