package com.cognizant.hams.repository;

import com.cognizant.hams.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientTypeAndRecipientIdOrderByCreatedAtDesc(Notification.RecipientType recipientType, Long recipientId);
}
