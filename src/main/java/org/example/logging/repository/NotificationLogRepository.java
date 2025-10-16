package org.example.logging.repository;

import org.example.logging.model.ENotificationStage;
import org.example.logging.model.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    Optional<NotificationLog> findByNotificationIdAndStage(Long notification_id, ENotificationStage stage);
    List<NotificationLog> findAllByNotificationId(Long notificationId);


}

