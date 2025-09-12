package com.cognizant.hams.controller;

import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.service.DoctorService;
import com.cognizant.hams.service.DoctorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;
//    public DoctorController(DoctorService doctorService){
//        this.doctorService = doctorService;
//    }
    @PostMapping("/doctor")
    public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody Doctor doctor){
        Doctor savedDoctor = doctorServiceImpl.createDoctor(doctor);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }
}
