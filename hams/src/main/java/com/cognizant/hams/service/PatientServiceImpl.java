package com.cognizant.hams.service;

import com.cognizant.hams.dto.PatientResponseDTO;
import com.cognizant.hams.dto.PatientDTO;
import com.cognizant.hams.entity.Patient;
import com.cognizant.hams.exception.APIException;
import com.cognizant.hams.exception.ResourceNotFoundException;
import com.cognizant.hams.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;


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

//    @Override
//    public List<DoctorResponseDto> getAllDoctors(){
//        List<Doctor> doctors= doctor.Repository.finalAll();
//        if(doctors.isEmpty()){
//            throw new APIException(("No Doctors Found"));
//        }
//
//        return doctors.stream()
//                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
//                .collect(Collectors.toList());
//    }

}
