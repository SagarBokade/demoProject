package com.cognizant.hams.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medical_records")
@Data
public class MedicalRecord {
    @Id
    private Long recordId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    private String reason;
    private String diagnosis;
    private String notes;
}
