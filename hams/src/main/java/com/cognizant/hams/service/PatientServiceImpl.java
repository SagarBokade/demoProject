package com.cognizant.hams.service;

import com.cognizant.hams.dto.DoctorResponseDTO;
import com.cognizant.hams.dto.PatientResponseDTO;
import com.cognizant.hams.dto.PatientDTO;
import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.entity.Patient;
import com.cognizant.hams.exception.APIException;
import com.cognizant.hams.exception.ResourceNotFoundException;
import com.cognizant.hams.repository.DoctorRepository;
import com.cognizant.hams.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;


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
        List<Doctor> doctors= doctorRepository.findAll();
        if(doctors.isEmpty()){
            throw new APIException(("No Doctors Found"));
        }

        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorResponseDTO> searchDoctorsBySpecialization(String specialization) {
        List<Doctor> doctorSpecialization = doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
        if(doctorSpecialization == null){
            throw new ResourceNotFoundException("Doctor","Specialization",specialization);
        }

        return doctorSpecialization.stream()
                .map(doctor -> modelMapper.map(doctor,DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorResponseDTO> searchDoctorsByName(String name) {
        List<Doctor> doctorName = doctorRepository.findByDoctorNameContainingIgnoreCase(name);
        if(doctorName == null){
            throw new ResourceNotFoundException("Doctor","Name",name);
        }

        return doctorName.stream()
                .map(doctor -> modelMapper.map(doctor,DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorResponseDTO> searchDoctors(String name, String specialization){
        List<Doctor> doctors = doctorRepository.findByDoctorNameAndSpecializationContainingIgnoreCase(name, specialization);
        if(doctors.isEmpty()){
            throw new APIException("No Doctor found");
        }
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }
}
