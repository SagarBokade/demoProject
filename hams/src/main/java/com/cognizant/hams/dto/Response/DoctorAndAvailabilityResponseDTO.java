package com.cognizant.hams.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class DoctorAndAvailabilityResponseDTO {
    private Long doctorId;
    private String contactNumber;
    private String doctorName;
    private String email;
    private String clinicAddress;

    private String specialization;

    private String qualification;
    private Integer yearOfExperience;
    private Time startTime;
    private Time endTime;
    private Date availableDate;
    private boolean available;
}
