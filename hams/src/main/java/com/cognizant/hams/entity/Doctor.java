package com.cognizant.hams.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "doctors")
@Data
public class Doctor {
    @Id
    private String doctorId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private String name;
    private String contactNumber;
    private String email;
}
