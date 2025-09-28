package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<Notification> getByCreator(String creator) {
        return repository.findByCreatedBy(creator);
    }

    public Optional<Notification> getById(Long id) {
        return repository.findById(id);
    }

    public Notification update(Long id, Notification updatedNotification) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setTitle(updatedNotification.getTitle());
                    existing.setDescription(updatedNotification.getDescription());
                    existing.setEventDate(updatedNotification.getEventDate());
                    existing.setType(updatedNotification.getType());
                    return repository.save(existing);
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
