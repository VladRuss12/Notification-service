package org.example.repository;

import org.example.model.NotificationSendLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationSendLogRepository extends JpaRepository<NotificationSendLog, Long> {

    Optional<NotificationSendLog> findByNotificationIdAndStage(Long notificationId, String stage);
    List<NotificationSendLog> findAllByNotificationId(Long notificationId);


}

