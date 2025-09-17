package com.cognizant.hams.service;

import com.cognizant.hams.dto.DoctorDTO;
import com.cognizant.hams.dto.DoctorResponseDTO;
import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.exception.APIException;
import com.cognizant.hams.exception.ResourceNotFoundException;
import com.cognizant.hams.repository.DoctorRepository;
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

//    private SpecializationRepository specializationRepository;

//    public DoctorServiceImpl(DoctorRepository doctorRepository, SpecializationRepository specializationRepository){
//        this.doctorRepository = doctorRepository;
//        this.specializationRepository = specializationRepository;
//    }

    @Override
    public DoctorResponseDTO createDoctor(DoctorDTO doctorDTO) {
        // Option A: If you're creating a new doctor, make sure the ID is null
        // and Hibernate will insert it.
        // If an ID is present, treat it as an update.
        Doctor doctor = modelMapper.map(doctorDTO,Doctor.class);
        if(doctorRepository.existsByDoctorNameAndSpecialization(doctor.getDoctorName(),doctor.getSpecialization())){
            throw new APIException("Doctor with this name and specialization does not exist.");
        }
        Doctor saveDoctor = doctorRepository.save(doctor);
        return modelMapper.map(saveDoctor,DoctorResponseDTO.class);
    }

    @Override
    public DoctorResponseDTO getDoctorById(Long doctorId) {
        Optional<Doctor> doctorOptional = doctorRepository.findByDoctorId(doctorId);
        if(doctorOptional.isEmpty()){
            throw new APIException("Doctor with doctorId " + doctorId + " does not exist.");
        }
        return modelMapper.map(doctorOptional,DoctorResponseDTO.class);
    }

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

    @Override
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
        if(doctorDTO.getAvailableDays() != null){
            existingDoctor.setAvailableDays(doctorDTO.getAvailableDays());
        }
        if(doctorDTO.getClinicAddress() != null){
            existingDoctor.setClinicAddress(doctorDTO.getClinicAddress());
        }
        if(doctorDTO.getEmail() != null){
            existingDoctor.setEmail(doctorDTO.getEmail());
        }

        doctorRepository.save(existingDoctor);
        return modelMapper.map(existingDoctor,DoctorResponseDTO.class);
    }

    @Override
    public DoctorResponseDTO deleteDoctor(Long doctorId){
       Doctor existingDoctor = doctorRepository.findByDoctorId(doctorId)
               .orElseThrow(() -> new ResourceNotFoundException("Doctor","doctorId", doctorId));
       doctorRepository.deleteById(doctorId);
       return modelMapper.map(existingDoctor,DoctorResponseDTO.class);
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
    public List<DoctorResponseDTO> searchDoctorsByNameAndSpecialization(String name, String specialization) {
        List<Doctor> doctors;

        if (name != null && !name.trim().isEmpty() && specialization != null && !specialization.trim().isEmpty()) {
            doctors = doctorRepository.findByDoctorNameContainingIgnoreCaseAndSpecializationContainingIgnoreCase(name, specialization);
        } else if (name != null && !name.trim().isEmpty()) {
            doctors = doctorRepository.findByDoctorNameContainingIgnoreCase(name);
        } else if (specialization != null && !specialization.trim().isEmpty()) {
            doctors = doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
        } else {
            doctors = doctorRepository.findAll();
        }

        if (doctors.isEmpty()) {
            throw new APIException("No doctors found with the specified criteria.");
        }

        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
                .collect(Collectors.toList());
    }


}