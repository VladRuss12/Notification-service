package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreateNotificationRequest;
import org.example.dto.NotificationResponse;
import org.example.dto.UpdateNotificationRequest;
import org.example.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse create(@Valid @RequestBody CreateNotificationRequest request) {
        return service.create(request);
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
            @Valid @RequestBody UpdateNotificationRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArchived() {
        service.deleteArchived();
    }
}
