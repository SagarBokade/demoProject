package com.cognizant.hams.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "specializations")
@Data
public class Specialization {
    @Id
    private String specializationId;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;
}