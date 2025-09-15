package com.cognizant.hams.repository;

import com.cognizant.hams.entity.Doctor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByDoctorName(@NotBlank @Size(min = 2, message = "Enter valid name") String doctorName);

    Optional<Doctor> findByDoctorId(@NotBlank Long doctorId);

    boolean existsByDoctorNameAndSpecialization(@NotBlank(message = "Doctor name is required")
                                                @Size(min = 3, max = 25, message = "Name must be between 3 and 25 characters") String doctorName,
                                                @NotBlank(message = "Specialization of a doctor is required")
                                                @Size(max = 30, message = "Specialization must not exceed 30 characters") String specialization);

    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);

    List<Doctor> findByDoctorNameContainingIgnoreCase(@NotBlank(message = "Doctor name is required")
                                                      @Size(min = 3, max = 25, message = "Name must be between 3 and 25 characters") String name);

    List<Doctor> findByDoctorNameAndSpecializationContainingIgnoreCase(String name, String specialization);
}
