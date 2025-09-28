package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;

    public Notification create(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        return repository.save(notification);
    }

    public List<Notification> getAll() {
        return repository.findAll();
    }
}

