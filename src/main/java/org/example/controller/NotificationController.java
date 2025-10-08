package org.example.controller;

import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.dto.UpdateNotificationRequest;
import org.example.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> create(@RequestBody CreateNotificationRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public List<NotificationResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/createdBy/{creator}")
    public List<NotificationResponse> getByCreator(@PathVariable String creator) {
        return service.getByCreator(creator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> update(@PathVariable Long id,
                                                       @RequestBody UpdateNotificationRequest request) {
        try {
            return ResponseEntity.ok(service.update(id, request));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
