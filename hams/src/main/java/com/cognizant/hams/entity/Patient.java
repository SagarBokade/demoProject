package com.cognizant.hams.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "patients")
@Data
public class Patient {
    @Id
    private String patientId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    private String name;
    private Date dateOfBirth;
    private String gender;
    private String contactNumber;
    private String email;
    private String status;
    private String Address;
}
