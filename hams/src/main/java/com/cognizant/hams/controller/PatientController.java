package com.cognizant.hams.controller;

import com.cognizant.hams.dto.PatientDTO;
import com.cognizant.hams.dto.PatientResponseDTO;
import com.cognizant.hams.service.PatientServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {


    private final PatientServiceImpl patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientDTO createDTO){
        PatientResponseDTO newPatient = patientService.createPatient(createDTO);
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable("id") Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("patientId") Long patientId,
                                                            @Valid @RequestBody PatientDTO patientUpdateDTO){
        PatientResponseDTO existingPatientDTO = patientService.updatePatient(patientId, patientUpdateDTO);
        return new ResponseEntity<>(existingPatientDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<PatientResponseDTO> deletePatient(@PathVariable("patientId") Long patientId){
        PatientResponseDTO deletePatientDTO = patientService.deletePatient(patientId);
        return new ResponseEntity<>(deletePatientDTO, HttpStatus.OK);
    }




}


