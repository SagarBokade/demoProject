package com.cognizant.hams.service;

import com.cognizant.hams.entity.Appointment;
import com.cognizant.hams.entity.Notification;

import java.util.List;

public interface NotificationService {
    void notifyDoctorOnAppointmentRequest(Appointment appointment);
    void notifyPatientOnAppointmentDecision(Appointment appointment, boolean confirmed, String reason);
    List<Notification> getNotificationForDoctor(Long doctorId);
    List<Notification> getNotificationForPatient(Long patientId);
    void markAsRead(Long notificationId);
}
