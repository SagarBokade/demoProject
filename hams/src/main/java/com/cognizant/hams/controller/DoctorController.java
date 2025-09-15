package com.cognizant.hams.controller;

import com.cognizant.hams.dto.DoctorDTO;
import com.cognizant.hams.dto.DoctorResponseDTO;
import com.cognizant.hams.entity.Doctor;
import com.cognizant.hams.service.DoctorService;
import com.cognizant.hams.service.DoctorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO){
        DoctorResponseDTO savedDoctor = doctorService.createDoctor(doctorDTO);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable("doctorId") Long doctorId){
        DoctorResponseDTO doctor = doctorService.getDoctorById(doctorId);
        return new ResponseEntity<>(doctor,HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctor(){
        List<DoctorResponseDTO> doctors = doctorService.getAllDoctor();
        return new ResponseEntity<>(doctors,HttpStatus.OK);
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable("doctorId") Long doctorId, @RequestBody DoctorDTO doctorDTO){
        DoctorResponseDTO updateDoctor = doctorService.updateDoctor(doctorId,doctorDTO);
        return new ResponseEntity<>(updateDoctor,HttpStatus.OK);

    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<DoctorResponseDTO> deleteDoctor(@PathVariable("doctorId") Long doctorId){
        DoctorResponseDTO deleteDoctor = doctorService.deleteDoctor(doctorId);
        return new ResponseEntity<>(deleteDoctor, HttpStatus.OK);
    }

    @GetMapping("/specializations")
    public ResponseEntity<List<DoctorResponseDTO>> searchDoctorsBySpecialization(@RequestParam("specialization") String specialization){
        List<DoctorResponseDTO> doctors = doctorService.searchDoctorsBySpecialization(specialization);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/doctorName")
    public ResponseEntity<List<DoctorResponseDTO>> searchDoctorsByName(@RequestParam("name") String name){
        List<DoctorResponseDTO> doctors = doctorService.searchDoctorsByName(name);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}
