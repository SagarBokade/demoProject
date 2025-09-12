package com.cognizant.hams.service;

import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.repository.DoctorRepository;
import com.cognizant.hams.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

//    @Autowired
    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor(Doctor doctor) {
        // Option A: If you're creating a new doctor, make sure the ID is null
        // and Hibernate will insert it.
        // If an ID is present, treat it as an update.
        if (doctor.getDoctorId() == null) {
            return doctorRepository.save(doctor);
        } else {
            // Option B: For updates, retrieve the existing entity, update it, and save.
            Doctor existingDoctor = doctorRepository.findById(doctor.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            existingDoctor.setDoctorName(doctor.getDoctorName());
            existingDoctor.setContactNumber(doctor.getContactNumber());
            existingDoctor.setEmail(doctor.getEmail());

            return doctorRepository.save(existingDoctor);
        }
    }
}