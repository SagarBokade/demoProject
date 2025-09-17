package com.cognizant.hams.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "prescriptions")
@Data
public class Prescription {
    @Id
    private Long prescriptionId;

    @ManyToOne
    @JoinColumn(name = "recordId")
    private MedicalRecord medicalRecord;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    private Date onsetDate;
    private String instructions;
}
