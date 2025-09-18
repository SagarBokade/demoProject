package com.cognizant.hams.controller;

import com.cognizant.hams.dto.AppointmentDTO;
import com.cognizant.hams.dto.AppointmentResponseDTO;
import com.cognizant.hams.entity.Notification;
import com.cognizant.hams.service.DoctorService;
import com.cognizant.hams.service.NotificationService;
import com.cognizant.hams.service.PatientService; // Or a dedicated AppointmentService
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AppointmentController {

    private final PatientService patientService;
    private final NotificationService notificationService;
    private final DoctorService doctorService;

    @PostMapping("/patients/{patientId}/appointments")
    public ResponseEntity<AppointmentResponseDTO> bookAppointment(
            @PathVariable("patientId") Long patientId,
            @Valid @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentResponseDTO newAppointment = patientService.bookAppointment(patientId, appointmentDTO);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/patients/{patientId}/appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsForPatient(
            @PathVariable("patientId") Long patientId) {
        List<AppointmentResponseDTO> appointments = patientService.getAppointmentsForPatient(patientId);
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieves a single appointment by its unique ID.
     */
    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(
            @PathVariable("appointmentId") Long appointmentId) {
        AppointmentResponseDTO appointment = patientService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appointment);
    }

    /**
     * Updates an existing appointment's details.
     */
    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(
            @PathVariable("appointmentId") Long appointmentId,
            @Valid @RequestBody AppointmentDTO appointmentUpdateDTO) {
        AppointmentResponseDTO updatedAppointment = patientService.updateAppointment(appointmentId, appointmentUpdateDTO);
        return ResponseEntity.ok(updatedAppointment);
    }

    /**
     * Cancels an appointment.
     * PATCH is used as it's a partial update (changing the status).
     */
    @PatchMapping("/appointments/{appointmentId}/cancel")
    public ResponseEntity<AppointmentResponseDTO> cancelAppointment(
            @PathVariable("appointmentId") Long appointmentId) {
        AppointmentResponseDTO canceledAppointment = patientService.cancelAppointment(appointmentId);
        return ResponseEntity.ok(canceledAppointment);
    }

    @PostMapping("/doctors/{doctorId}/appointments/{appointmentId}/confirm")
    public ResponseEntity<AppointmentResponseDTO> confirmAppointment(@PathVariable Long doctorId, @PathVariable Long appointmentId){
        AppointmentResponseDTO responseDTO = doctorService.confirmAppointment(doctorId, appointmentId);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/doctors/{doctorId}/appointments/{appointmentId}/reject")
    public ResponseEntity<AppointmentResponseDTO> rejectAppointment(@PathVariable Long doctorId,
                                                                    @PathVariable Long appointmentId,
                                                                    @RequestParam(value = "reason", required = false) String reason){
        AppointmentResponseDTO responseDTO = doctorService.rejectAppointment(doctorId, appointmentId, reason);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/doctors/{doctorId}/notifications")
    public ResponseEntity<List<Notification>> getDoctorNotifications(@PathVariable Long doctorId){
        return ResponseEntity.ok(notificationService.getNotificationForDoctor(doctorId));
    }
}