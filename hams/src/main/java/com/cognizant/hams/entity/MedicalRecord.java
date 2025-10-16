package com.cognizant.hams.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalRecordId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    private String diagnosis;
    private String treatment;
    private String prescription;
    private String notes;

    private String reason;
    private LocalDateTime createdAt;
    
    @Column(name = "record_date")
    private LocalDateTime recordDate = LocalDateTime.now();
    
    @Column(name = "follow_up_date")
    private LocalDateTime followUpDate;
}
