package org.example.logging.service;

import lombok.RequiredArgsConstructor;
import org.example.logging.dto.NotificationLogResponse;
import org.example.model.Notification;
import org.example.logging.model.ENotificationStage;
import org.example.logging.model.NotificationLog;
import org.example.logging.repository.NotificationLogRepository;
import org.example.logging.mapper.NotificationLogMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationLogService {

    private final NotificationLogRepository logRepository;

    public NotificationLogResponse createLog(Notification notification, String stage) {
        NotificationLog log = NotificationLog.builder()
                .notification(notification)
                .stage(ENotificationStage.valueOf(stage.toUpperCase()))
                .sentAt(LocalDateTime.now())
                .build();

        logRepository.save(log);
        return NotificationLogMapper.toDto(log);
    }

    public boolean isSent(Notification notification, ENotificationStage stage) {
        return logRepository.findByNotificationIdAndStage(notification.getId(), stage).isPresent();
    }

    public List<NotificationLogResponse> getLogsByNotification(Notification notification) {
        List<NotificationLog> logs = logRepository.findAllByNotificationId(notification.getId());
        return logs.stream()
                .map(NotificationLogMapper::toDto)
                .toList();
    }
}
