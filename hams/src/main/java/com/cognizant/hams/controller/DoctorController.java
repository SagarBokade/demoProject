package com.cognizant.hams.controller;

import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.service.DoctorService;
import com.cognizant.hams.service.DoctorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

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

    @GetMapping("/{doctorId}")
    public ResponseEntity<Doctor> getDoctoryById(@PathVariable("doctorId") Long doctorId){
        try{
            Doctor doctor = doctorServiceImpl.getDoctorById(doctorId);
            return new ResponseEntity<>(doctor,HttpStatus.OK);
        }
        catch(RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctor(){
        List<Doctor> doctors = doctorServiceImpl.getAllDoctor();
        return new ResponseEntity<>(doctors,HttpStatus.OK);
    }
}
