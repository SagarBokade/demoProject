package com.cognizant.hams.service.Impl;

import com.cognizant.hams.dto.Request.AdminUserRequestDTO;
import com.cognizant.hams.dto.Request.DoctorDTO;
import com.cognizant.hams.dto.Response.DoctorResponseDTO;
import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.entity.User;
import com.cognizant.hams.exception.APIException;
import com.cognizant.hams.exception.ResourceNotFoundException;
import com.cognizant.hams.repository.DoctorRepository;
import com.cognizant.hams.repository.UserRepository;
import com.cognizant.hams.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;




    @Override
    public DoctorResponseDTO createDoctor(AdminUserRequestDTO doctorDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuthorized = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR") || a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAuthorized) {
            throw new AccessDeniedException("You are not authorized to create a new doctor.");
        }

        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

        String currentUsername = authentication.getName();
        User loggedInUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));

        doctor.setUser(loggedInUser);

        Doctor savedDoctor = doctorRepository.save(doctor);
        return modelMapper.map(savedDoctor, DoctorResponseDTO.class);
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
                .toList();
    }

    // Update Doctor

    @Override
    public DoctorResponseDTO updateDoctor(Long doctorId, DoctorDTO doctorDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        Doctor loggedInDoctor = (Doctor) doctorRepository.findByUser_Username(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "username", currentUsername));

        if (!loggedInDoctor.getDoctorId().equals(doctorId)) {
            throw new AccessDeniedException("You are not authorized to update another doctor's details.");
        }

        if (doctorDTO.getDoctorName() != null) {
            loggedInDoctor.setDoctorName(doctorDTO.getDoctorName());
        }
        if (doctorDTO.getQualification() != null) {
            loggedInDoctor.setQualification(doctorDTO.getQualification());
        }
        if (doctorDTO.getSpecialization() != null) {
            loggedInDoctor.setSpecialization(doctorDTO.getSpecialization());
        }
        if (doctorDTO.getYearOfExperience() != null) {
            loggedInDoctor.setYearOfExperience(doctorDTO.getYearOfExperience());
        }
        if (doctorDTO.getClinicAddress() != null) {
            loggedInDoctor.setClinicAddress(doctorDTO.getClinicAddress());
        }
        if (doctorDTO.getEmail() != null) {
            loggedInDoctor.setEmail(doctorDTO.getEmail());
        }
        if (doctorDTO.getContactNumber() != null) {
            loggedInDoctor.setContactNumber(doctorDTO.getContactNumber());
        }

        doctorRepository.save(loggedInDoctor);
        return modelMapper.map(loggedInDoctor, DoctorResponseDTO.class);
    }

    // Delete Doctor
    @Override
    public DoctorResponseDTO deleteDoctor(Long doctorId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Doctor loggedInDoctor = (Doctor) doctorRepository.findByUser_Username(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "username", currentUsername));

        if (!isAdmin && !loggedInDoctor.getDoctorId().equals(doctorId)) {
            throw new AccessDeniedException("You are not authorized to delete another doctor's profile.");
        }

        Doctor existingDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "doctorId", doctorId));

        doctorRepository.deleteById(doctorId);
        return modelMapper.map(existingDoctor, DoctorResponseDTO.class);
    }

    // Search Doctors By Specialization

    @Override
    public List<DoctorResponseDTO> searchDoctorsBySpecialization(String specialization) {
        List<Doctor> doctorSpecialization = doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
        if(doctorSpecialization == null){
            throw new ResourceNotFoundException("Doctor","Specialization",specialization);
        }

        return doctorSpecialization.stream()
                .map(doctor -> modelMapper.map(doctor,DoctorResponseDTO.class))
                .toList();
    }

    // Search Doctors By Name

    @Override
    public List<DoctorResponseDTO> searchDoctorsByName(String name) {
        List<Doctor> doctorName = doctorRepository.findByDoctorNameContainingIgnoreCase(name);
        if(doctorName == null){
            throw new ResourceNotFoundException("Doctor","Name",name);
        }

        return doctorName.stream()
                .map(doctor -> modelMapper.map(doctor,DoctorResponseDTO.class))
                .toList();
    }
}