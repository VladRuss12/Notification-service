package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.dto.UpdateNotificationRequest;
import org.example.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

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
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/{id}/with-logs")
    public ResponseEntity<NotificationResponse> getWithLogs(@PathVariable Long id) {
        return ResponseEntity.ok(service.getWithLogsById(id));
    }

    @GetMapping("/createdBy/{creator}")
    public List<NotificationResponse> getByCreator(@PathVariable String creator) {
        return service.getByCreator(creator);
    }

    @GetMapping("/archived")
    public List<NotificationResponse> getArchived(@RequestParam(defaultValue = "true") boolean archived) {
        return service.getByArchived(archived);
    }

    @GetMapping("/createdBy/{creator}/archived")
    public List<NotificationResponse> getByCreatorAndArchived(
            @PathVariable String creator,
            @RequestParam(defaultValue = "false") boolean archived) {
        return service.getByCreatorAndArchived(creator, archived);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateNotificationRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<NotificationResponse> archive(@PathVariable Long id) {
        return ResponseEntity.ok(service.archiveById(id));
    }

    @PatchMapping("/{id}/unarchive")
    public ResponseEntity<NotificationResponse> unarchive(@PathVariable Long id) {
        return ResponseEntity.ok(service.unarchiveById(id));
    }

    @DeleteMapping("/archived")
    public ResponseEntity<Void> deleteArchived() {
        service.deleteArchived();
        return ResponseEntity.noContent().build();
    }
}
