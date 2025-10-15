package org.example.mapper;

import org.example.dto.*;
import org.example.model.NotificationSendLog;

public class NotificationSendLogMapper {
    public static NotificationSendLogResponse toDto(NotificationSendLog log) {
        NotificationSendLogResponse dto = new NotificationSendLogResponse();
        dto.setId(log.getId());
        dto.setNotificationId(log.getNotification().getId());
        dto.setSentAt(log.getSentAt());
        dto.setStage(log.getStage());
        return dto;
    }
}

