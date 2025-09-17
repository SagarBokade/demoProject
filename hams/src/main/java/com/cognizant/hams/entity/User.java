package com.cognizant.hams.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private String userId;
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    public String getPatientId() {

        return "";
    }
}
