package com.cognizant.hams.service;

import com.cognizant.hams.dto.*;
import com.cognizant.hams.entity.Appointment;
import com.cognizant.hams.entity.AppointmentStatus;
import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.entity.Patient;
import com.cognizant.hams.exception.APIException;
import com.cognizant.hams.exception.ResourceNotFoundException;
import com.cognizant.hams.repository.AppointmentRepository;
import com.cognizant.hams.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;


    @Override
    public PatientResponseDTO createPatient(PatientDTO patientCreateDTO){
        Patient patient = modelMapper.map(patientCreateDTO, Patient.class);
        if(patientRepository.existsByEmailAndContactNumber(patient.getEmail(), patient.getContactNumber())){
            throw new APIException("Person with email " + patient.getEmail() + " and contact number " + patient.getContactNumber() + "already exists");
        }
        Patient savedPatient = patientRepository.save(patient);
        return modelMapper.map(patient, PatientResponseDTO.class);
    }


    @Override
    public PatientResponseDTO getPatientById(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new ResourceNotFoundException("Patient", "patientId", patientId));
        return modelMapper.map(patient, PatientResponseDTO.class);
    }

    @Override
    public PatientResponseDTO updatePatient(Long patientId, PatientDTO patientUpdateDTO) {
        Patient existingPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "patientId", patientId));
        if(patientUpdateDTO.getContactNumber() != null){
            existingPatient.setContactNumber(patientUpdateDTO.getContactNumber());
        }
        if(patientUpdateDTO.getEmail() != null){
            existingPatient.setEmail(patientUpdateDTO.getEmail());
        }
        if(patientUpdateDTO.getAddress() != null){
            existingPatient.setAddress(patientUpdateDTO.getAddress());
        }
        if(patientUpdateDTO.getGender() != null){
            existingPatient.setGender(patientUpdateDTO.getGender());
        }
        if(patientUpdateDTO.getName() != null){
            existingPatient.setName(patientUpdateDTO.getName());
        }
        if(patientUpdateDTO.getBloodGroup() != null){
            existingPatient.setBloodGroup(patientUpdateDTO.getBloodGroup());
        }
        if(patientUpdateDTO.getDateOfBirth() != null){
            existingPatient.setDateOfBirth(patientUpdateDTO.getDateOfBirth());
        }
        return modelMapper.map(existingPatient, PatientResponseDTO.class);
    }

    @Override
    public PatientResponseDTO deletePatient(Long patientId){
        Patient deletePatient = patientRepository.findById(patientId)
                .orElseThrow(()-> new ResourceNotFoundException("Patient", "patientID", patientId));
        patientRepository.delete(deletePatient);
        return modelMapper.map(deletePatient, PatientResponseDTO.class);
    }

    @Override
    public List<DoctorResponseDTO> getAllDoctors(){
        return doctorService.getAllDoctor();
    }

    @Override
    public List<DoctorResponseDTO> searchDoctorsByNameAndSpecialization(String name, String specialization) {
        return doctorService.searchDoctorsByNameAndSpecialization(name, specialization);
    }

    @Override
    public List<DoctorResponseDTO> searchDoctorByName(String name) {
        return doctorService.searchDoctorsByName(name);
    }

    @Override
    public List<DoctorResponseDTO> searchDoctorBySpecialization(String specialization) {
        return doctorService.searchDoctorsBySpecialization(specialization);
    }


    @Override
    public AppointmentResponseDTO bookAppointment(Long patientId, AppointmentDTO appointmentDTO) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "Id", patientId));

        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "Id", appointmentDTO.getDoctorId()));

        // **LOGIC UPDATE HERE**
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setStartTime(appointmentDTO.getStartTime()); // Updated
        appointment.setEndTime(appointmentDTO.getEndTime());     // Updated
        appointment.setReason(appointmentDTO.getReason());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return modelMapper.map(savedAppointment, AppointmentResponseDTO.class);
    }

    @Override
    public AppointmentResponseDTO updateAppointment(Long appointmentId, AppointmentDTO appointmentUpdateDTO) {
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "Id", appointmentId));

        if (appointmentUpdateDTO.getDoctorId() != null && !existingAppointment.getDoctor().getDoctorId().equals(appointmentUpdateDTO.getDoctorId())) {
            Doctor newDoctor = doctorRepository.findById(appointmentUpdateDTO.getDoctorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor", "Id", appointmentUpdateDTO.getDoctorId()));
            existingAppointment.setDoctor(newDoctor);
        }

        // **LOGIC UPDATE HERE**
        if (appointmentUpdateDTO.getAppointmentDate() != null) {
            existingAppointment.setAppointmentDate(appointmentUpdateDTO.getAppointmentDate());
        }
        if (appointmentUpdateDTO.getStartTime() != null) {
            existingAppointment.setStartTime(appointmentUpdateDTO.getStartTime()); // Updated
        }
        if (appointmentUpdateDTO.getEndTime() != null) {
            existingAppointment.setEndTime(appointmentUpdateDTO.getEndTime());     // Updated
        }
        if (appointmentUpdateDTO.getReason() != null) {
            existingAppointment.setReason(appointmentUpdateDTO.getReason());
        }

        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);

        return modelMapper.map(updatedAppointment, AppointmentResponseDTO.class);
    }

    // ... (The rest of your service methods: cancelAppointment, getAppointmentById, etc. remain unchanged)

    @Override
    public AppointmentResponseDTO cancelAppointment(Long appointmentId) {
        // ... (no changes needed here)
        Appointment appointmentToCancel = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "Id", appointmentId));

        if (appointmentToCancel.getStatus() == AppointmentStatus.COMPLETED || appointmentToCancel.getStatus() == AppointmentStatus.CANCELED) {
            throw new APIException("Appointment cannot be canceled as it is already " + appointmentToCancel.getStatus());
        }

        appointmentToCancel.setStatus(AppointmentStatus.CANCELED);
        Appointment canceledAppointment = appointmentRepository.save(appointmentToCancel);

        return modelMapper.map(canceledAppointment, AppointmentResponseDTO.class);
    }

    @Override
    public AppointmentResponseDTO getAppointmentById(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "Id", appointmentId));
        return modelMapper.map(appointment, AppointmentResponseDTO.class);
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsForPatient(Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient", "Id", patientId);
        }

        List<Appointment> appointments = appointmentRepository.findByPatient_PatientId(patientId);

        return appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .collect(Collectors.toList());
    }
}




