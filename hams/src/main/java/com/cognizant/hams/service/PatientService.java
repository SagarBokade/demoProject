package com.cognizant.hams.service;

import com.cognizant.hams.dto.DoctorResponseDTO;
import com.cognizant.hams.dto.PatientDTO;
import com.cognizant.hams.dto.PatientResponseDTO;

import java.util.List;

public interface PatientService {
    PatientResponseDTO createPatient(PatientDTO patientCreateDTO);
    PatientResponseDTO getPatientById(Long patientId);
    PatientResponseDTO updatePatient(Long patientId, PatientDTO patientUpdateDTO);
    PatientResponseDTO deletePatient(Long patientId);

    //    --- Doctor Search (from patient perspective) ---
    List<DoctorResponseDTO> getAllDoctors();
    List<DoctorResponseDTO> searchDoctorsBySpecialization(String specialization);

    List<DoctorResponseDTO> searchDoctorsByName(String name);
    List<DoctorResponseDTO> searchDoctors(String name, String specialization);

//  --- Appointments ---
//    AppointmentResponseDTO getAppointmentById(Long appointmentId);
//    AppointmentResponseDTO bookAppointment(Long patientId, AppointmentCreateDTO appointmentCreateDTO);
//    AppointmentResponseDTO updateAppointment(Long appointmentId, AppointmentUpdateDTO appointmentUpdateDTO);
//    AppointmentResponseDTO cancelAppointment(Long appointmentId);

//    --- Doctor Availability (helper) ---
//    List<AvailabilityDTO> getDoctorAvailability(Long doctorId);
}
