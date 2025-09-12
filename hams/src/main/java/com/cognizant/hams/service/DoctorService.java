package com.cognizant.hams.service;

import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.entity.DoctorAvailability;
import org.springframework.boot.availability.AvailabilityState;

import java.util.List;

public interface DoctorService {

    // Basic Curd operation
    Doctor createDoctor(Doctor doctor);
//    Doctor getDoctorById(Long doctorId);
//    List<Doctor> getAllDoctor();
//    Doctor updateDoctor(Long doctorId,Doctor doctor);
//    void deleteDoctor(Long doctorId);
//
//    // Domain Specific
//    List<Doctor> getDoctorsBySpecialization(Long SpecializationId);
//    Doctor addSpecialization(Long doctorId,Long SpecializationId);
//    Doctor removeSpecialization(Long doctorId, Long SpecializatoinId);
//
//    // Avaialability Management
//    DoctorAvailability addAvailability(Long id,DoctorAvailability slot);
//    List<DoctorAvailability> getAvailability(Long doctorId);
//    void deleteAvailabilitySlot(Long doctorId);
}
