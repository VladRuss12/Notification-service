package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.logging.dto.NotificationLogResponse;
import org.example.dto.UpdateNotificationRequest;
import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.example.logging.service.NotificationLogService;
import org.example.service.sender.HttpNotificationSenderService;
import org.example.throwable.BadRequestException;
import org.example.throwable.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final HttpNotificationSenderService sender;
    private final NotificationRepository repository;
    private final NotificationLogService logService;

    // --- CRUD ---
    public NotificationResponse create(CreateNotificationRequest request) {
        if (request.getEventDate() == null) {
            throw new BadRequestException("Event date must not be null");
        }
        Notification notification = org.example.mapper.NotificationMapper.fromCreateDto(request);
        notification.setCreatedAt(LocalDateTime.now());
        return org.example.mapper.NotificationMapper.toDto(repository.save(notification));
    }

    public List<NotificationResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(org.example.mapper.NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getByCreator(String creator) {
        return repository.findByCreatedBy(creator)
                .stream()
                .map(org.example.mapper.NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public NotificationResponse getById(Long id) {
        return repository.findById(id)
                .map(org.example.mapper.NotificationMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
    }

    public NotificationResponse getWithLogsById(Long id) {
        return repository.findById(id)
                .map(notification -> {
                    List<NotificationLogResponse> logs = logService.getLogsByNotification(notification);
                    return org.example.mapper.NotificationMapper.toDto(notification, logs);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
    }


    public List<Notification> getActiveNotifications() {
        return repository.findByArchived(false);
    }


    public NotificationResponse update(Long id, UpdateNotificationRequest request) {
        if (request.getEventDate() == null) {
            throw new BadRequestException("Event date must not be null");
        }
        return repository.findById(id)
                .map(existing -> {
                    org.example.mapper.NotificationMapper.updateEntity(existing, request);
                    return org.example.mapper.NotificationMapper.toDto(repository.save(existing));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
    }

    public void delete(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
        repository.delete(notification);
    }

    public List<NotificationResponse> getByArchived(boolean archived) {
        return repository.findByArchived(archived)
                .stream()
                .map(org.example.mapper.NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getByCreatorAndArchived(String creator, boolean archived) {
        return repository.findByCreatedByAndArchived(creator, archived)
                .stream()
                .map(org.example.mapper.NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public NotificationResponse archiveById(Long id) {
        return repository.findById(id)
                .map(notification -> {
                    notification.setArchived(true);
                    return org.example.mapper.NotificationMapper.toDto(repository.save(notification));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
    }

    public NotificationResponse unarchiveById(Long id) {
        return repository.findById(id)
                .map(notification -> {
                    notification.setArchived(false);
                    return org.example.mapper.NotificationMapper.toDto(repository.save(notification));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Notification", id));
    }

    public void deleteArchived() {
        List<Notification> archived = repository.findByArchived(true);
        repository.deleteAll(archived);
    }

    // --- Отправка уведомлений с проверкой дублирования ---
    public NotificationLogResponse sendNotificationIfNotSent(Notification notification, String stage) {
        if (logService.isSent(notification, stage)) {
            throw new BadRequestException("Notification already sent at stage: " + stage);
        }

        sender.send(notification, stage);

        return logService.createLog(notification, stage);
    }
}
