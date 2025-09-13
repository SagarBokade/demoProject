package com.cognizant.hams.repository;

import com.cognizant.hams.entity.Doctor;
import com.sun.source.doctree.DocCommentTree;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByDoctorName(@NotBlank @Size(min = 2, message = "Enter valid name") String doctorName);

    Optional<Doctor> findByDoctorId(@NotBlank Long doctorId);

}
