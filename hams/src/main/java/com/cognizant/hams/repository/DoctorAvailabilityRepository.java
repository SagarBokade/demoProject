package com.cognizant.hams.repository;

import com.cognizant.hams.dto.Response.DoctorAvailabilityResponseDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.hams.entity.DoctorAvailability;

import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {

    List<DoctorAvailability> findByDoctorDoctorId(Long doctorId);

    DoctorAvailabilityResponseDTO deleteByAvailabilityId(@NotNull Long availabilityId);
}
