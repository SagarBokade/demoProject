package com.cognizant.hams.service.Impl;

import com.cognizant.hams.dto.Request.DoctorDTO;
import com.cognizant.hams.dto.Response.DoctorResponseDTO;
import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.exception.APIException;
import com.cognizant.hams.exception.ResourceNotFoundException;
import com.cognizant.hams.repository.DoctorRepository;
import com.cognizant.hams.service.DoctorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    // Create Doctor //

    @Override
    @Transactional
    public DoctorResponseDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = modelMapper.map(doctorDTO,Doctor.class);
        Doctor saveDoctor = doctorRepository.save(doctor);
        return modelMapper.map(saveDoctor,DoctorResponseDTO.class);
    }

    // Get Doctor By I'd:

    @Override
    public DoctorResponseDTO getDoctorById(Long doctorId) {
        Optional<Doctor> doctorOptional = doctorRepository.findByDoctorId(doctorId);
        if(doctorOptional.isEmpty()){
            throw new APIException("Doctor with doctorId " + doctorId + " does not exist.");
        }
        return modelMapper.map(doctorOptional,DoctorResponseDTO.class);
    }

    // Get All Doctor

    @Override
    public List<DoctorResponseDTO> getAllDoctor(){
        List<Doctor> doctors = doctorRepository.findAll();
        if(doctors.isEmpty()){
            throw new APIException("No Doctor Available");
        }
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor,DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Update Doctor

    @Override
    @Transactional
    public DoctorResponseDTO updateDoctor(Long doctorId, DoctorDTO doctorDTO) {
        Doctor existingDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "doctorId", doctorId));

        if(doctorDTO.getDoctorName() != null){
            existingDoctor.setDoctorName(doctorDTO.getDoctorName());
        }
        if(doctorDTO.getQualification() != null){
            existingDoctor.setQualification(doctorDTO.getQualification());
        }
        if(doctorDTO.getSpecialization() != null){
            existingDoctor.setSpecialization(doctorDTO.getSpecialization());
        }
        if(doctorDTO.getYearOfExperience() != null){
            existingDoctor.setYearOfExperience(doctorDTO.getYearOfExperience());
        }
        if(doctorDTO.getClinicAddress() != null){
            existingDoctor.setClinicAddress(doctorDTO.getClinicAddress());
        }
        if(doctorDTO.getEmail() != null){
            existingDoctor.setEmail(doctorDTO.getEmail());
        }
        if(doctorDTO.getContactNumber() != null){
            existingDoctor.setContactNumber(doctorDTO.getContactNumber());
        }

        doctorRepository.save(existingDoctor);
        return modelMapper.map(existingDoctor,DoctorResponseDTO.class);
    }

    // Delete Doctor

    @Override
    @Transactional
    public DoctorResponseDTO deleteDoctor(Long doctorId){
        Doctor existingDoctor = doctorRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor","doctorId", doctorId));
        doctorRepository.deleteById(doctorId);
        return modelMapper.map(existingDoctor,DoctorResponseDTO.class);
    }

    // Search Doctors By Specialization

    @Override
    public List<DoctorResponseDTO> searchDoctorsBySpecialization(String specialization) {
        List<Doctor> doctorSpecialization = doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
        if(doctorSpecialization.isEmpty()){
            throw new APIException("No doctor found with specialization: " + specialization);
        }

        return doctorSpecialization.stream()
                .map(doctor -> modelMapper.map(doctor,DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Search Doctors By Name

    @Override
    public List<DoctorResponseDTO> searchDoctorsByName(String name) {
        List<Doctor> doctorName = doctorRepository.findByDoctorNameContainingIgnoreCase(name);
        if(doctorName == null){
            throw new ResourceNotFoundException("Doctor","Name",name);
        }

        if(doctorName.isEmpty()){
            throw new APIException("No doctor found with name: " + name);
        }

        return doctorName.stream()
                .map(doctor -> modelMapper.map(doctor,DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }
}