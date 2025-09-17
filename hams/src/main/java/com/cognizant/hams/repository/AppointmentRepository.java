package com.cognizant.hams.repository;

import com.cognizant.hams.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient_PatientId(Long patientId);
}
