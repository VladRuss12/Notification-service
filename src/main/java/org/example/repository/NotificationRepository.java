package org.example.repository;
import org.example.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAll();
    List<Notification> findByCreatedBy(String createdBy);
    List<Notification> findByArchived(boolean archived);
    List<Notification> findByCreatedByAndArchived(String createdBy, boolean archived);
}

