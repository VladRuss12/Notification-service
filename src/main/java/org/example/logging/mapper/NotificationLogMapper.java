package org.example.logging.mapper;

import org.example.logging.dto.NotificationLogResponse;
import org.example.logging.model.NotificationLog;

public class NotificationLogMapper {
    public static NotificationLogResponse toDto(NotificationLog log) {
        NotificationLogResponse dto = new NotificationLogResponse();
        dto.setId(log.getId());
        dto.setNotificationId(log.getNotification().getId());
        dto.setSentAt(log.getSentAt());
        dto.setStage(log.getStage().name());
        return dto;
    }
}

