package com.cognizant.hams.service.Impl;

import com.cognizant.hams.entity.Appointment;
import com.cognizant.hams.entity.Notification;
import com.cognizant.hams.repository.NotificationRepository;
import com.cognizant.hams.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void notifyDoctorOnAppointmentRequest(Appointment appointment) {
        Notification notification = new Notification();
        notification.setAppointment(appointment);
        notification.setRecipientType(Notification.RecipientType.DOCTOR);
        notification.setRecipientId(appointment.getDoctor().getDoctorId());
        notification.setTitle("New appointment request");
        notification.setMessage("Patient " + appointment.getPatient().getName() +
                " request an appointment on " + appointment.getAppointmentDate() +
                " from " + appointment.getStartTime() + "to " + appointment.getEndTime());
        notificationRepository.save(notification);
    }

    @Override
    public void notifyPatientOnAppointmentDecision(Appointment appointment,
                                                   boolean confirmed, String reason) {
        Notification notification = new Notification();
        notification.setAppointment(appointment);
        notification.setRecipientType(Notification.RecipientType.PATIENT);
        notification.setRecipientId(appointment.getPatient().getPatientId());
        notification.setTitle(confirmed ? "Appointment confirmed" : "Appointment rejected");
        String message = confirmed ? "Your appointment with Dr. " + appointment.getDoctor()
                .getDoctorName() + " on " + appointment.getAppointmentDate() + " is confirmed." :
                "Your appointment with Dr. " + appointment.getDoctor().getDoctorName() + " on " +
                        appointment.getAppointmentDate() + " was rejected. Reason: " +
                        (reason == null ? "N/A" : reason);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationForDoctor(Long doctorId) {
        return notificationRepository.findByRecipientTypeAndRecipientIdOrderByCreatedAtDesc(Notification.RecipientType.DOCTOR, doctorId);
    }

    @Override
    public List<Notification> getNotificationForPatient(Long patientId) {
        return notificationRepository.findByRecipientTypeAndRecipientIdOrderByCreatedAtDesc(Notification.RecipientType.PATIENT, patientId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }
}
