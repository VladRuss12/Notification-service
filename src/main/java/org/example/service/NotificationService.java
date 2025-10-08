package org.example.service;

import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.dto.UpdateNotificationRequest;
import org.example.mapper.NotificationMapper;
import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

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
}
