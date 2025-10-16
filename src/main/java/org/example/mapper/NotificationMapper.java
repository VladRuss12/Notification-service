package org.example.mapper;

import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.dto.UpdateNotificationRequest;
import org.example.logging.dto.NotificationLogResponse;
import org.example.model.Notification;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationMapper {

    public static NotificationResponse toDto(Notification entity) {
        NotificationResponse dto = new NotificationResponse();
        dto.setId(entity.getId());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setType(entity.getType());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setEventDate(entity.getEventDate());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setArchived(entity.isArchived());
        return dto;
    }

    public static NotificationResponse toDto(Notification entity, List<NotificationLogResponse> logs) {
        NotificationResponse dto = toDto(entity);

        dto.setSendLogs(
                logs == null ? List.of() : logs.stream()
                        .map(log -> new NotificationResponse.SendLogInfo(
                                log.getSentAt(),
                                log.getStage()
                        ))
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public static Notification fromCreateDto(CreateNotificationRequest dto) {
        Notification notification = new Notification();
        notification.setCreatedBy(dto.getCreatedBy());
        notification.setType(dto.getType());
        notification.setTitle(dto.getTitle());
        notification.setDescription(dto.getDescription());
        notification.setEventDate(dto.getEventDate());
        return notification;
    }

    public static void updateEntity(Notification entity, UpdateNotificationRequest dto) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setEventDate(dto.getEventDate());
        entity.setArchived(dto.isArchived());
    }
}
