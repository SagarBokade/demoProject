package com.cognizant.hams.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "doctors")
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Version
    private Long version;

    private String doctorName;
    private String contactNumber;
    private String email;
}
