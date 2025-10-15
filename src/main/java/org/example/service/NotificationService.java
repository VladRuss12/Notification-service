package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.dto.UpdateNotificationRequest;
import org.example.mapper.NotificationMapper;
import org.example.model.Notification;
import org.example.model.NotificationSendLog;
import org.example.repository.NotificationRepository;
import org.example.repository.NotificationSendLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationSendLogRepository logRepository;


    public NotificationResponse create(CreateNotificationRequest request) {
        Notification notification = NotificationMapper.fromCreateDto(request);
        notification.setCreatedAt(LocalDateTime.now());
        return NotificationMapper.toDto(repository.save(notification));
    }

    public List<NotificationResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getByCreator(String creator) {
        return repository.findByCreatedBy(creator)
                .stream()
                .map(NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<NotificationResponse> getById(Long id) {
        return repository.findById(id).map(NotificationMapper::toDto);
    }

    public NotificationResponse update(Long id, UpdateNotificationRequest request) {
        return repository.findById(id)
                .map(existing -> {
                    NotificationMapper.updateEntity(existing, request);
                    return NotificationMapper.toDto(repository.save(existing));
                })
                .orElseThrow(() -> new RuntimeException("Notification not found with id " + id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Notification not found with id " + id);
        }
        repository.deleteById(id);
    }
    public List<NotificationResponse> getByArchived(boolean archived) {
        return repository.findByArchived(archived)
                .stream()
                .map(NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getByCreatorAndArchived(String creator, boolean archived) {
        return repository.findByCreatedByAndArchived(creator, archived)
                .stream()
                .map(NotificationMapper::toDto)
                .collect(Collectors.toList());
    }
    public NotificationResponse archiveById(Long id) {
        return repository.findById(id)
                .map(notification -> {
                    notification.setArchived(true);
                    return NotificationMapper.toDto(repository.save(notification));
                })
                .orElseThrow(() -> new RuntimeException("Notification not found with id " + id));
    }

    public NotificationResponse unarchiveById(Long id) {
        return repository.findById(id)
                .map(notification -> {
                    notification.setArchived(false);
                    return NotificationMapper.toDto(repository.save(notification));
                })
                .orElseThrow(() -> new RuntimeException("Notification not found with id " + id));
    }

    public void deleteArchived() {
        List<Notification> archived = repository.findByArchived(true);
        repository.deleteAll(archived);
    }
    public Optional<NotificationResponse> getWithLogsById(Long id) {
        return repository.findById(id)
                .map(notification -> {
                    List<NotificationSendLog> logs = logRepository.findAllByNotificationId(notification.getId());
                    return NotificationMapper.toDto(notification, logs);
                });
    }

}
