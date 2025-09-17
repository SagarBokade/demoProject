package com.cognizant.hams.service;

import com.cognizant.hams.dto.DoctorDTO;
import com.cognizant.hams.dto.DoctorResponseDTO;

import java.util.List;

public interface DoctorService {

    // Basic Curd operation
    DoctorResponseDTO createDoctor(DoctorDTO doctorDto);
    DoctorResponseDTO getDoctorById(Long doctorId);
    List<DoctorResponseDTO> getAllDoctor();
    DoctorResponseDTO updateDoctor(Long doctorId,DoctorDTO doctorDto);
    DoctorResponseDTO deleteDoctor(Long doctorId);


    // Domain Specific
    List<DoctorResponseDTO> searchDoctorsBySpecialization(String specialization);
    List<DoctorResponseDTO> searchDoctorsByName(String name);
    List<DoctorResponseDTO> searchDoctorsByNameAndSpecialization(String name, String specialization);

//
//    // Avaialability Management
//    DoctorAvailability addAvailability(Long id,DoctorAvailability slot);
//    List<DoctorAvailability> getAvailability(Long doctorId);
//    void deleteAvailabilitySlot(Long doctorId);
}
