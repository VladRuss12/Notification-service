package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.SendNotificationRequest;
import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.example.service.sender.HttpNotificationSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
@Validated
public class NotificationSenderController {

    private final HttpNotificationSenderService senderService;
    private final NotificationRepository repository;

    @PostMapping
    public ResponseEntity<String> sendNotification(@Valid @RequestBody SendNotificationRequest request) {
        Notification notification = repository.findById(request.getNotificationId())
                .orElseThrow(() -> new IllegalArgumentException("Уведомление не найдено"));

        senderService.send(notification, request.getStage());
        return ResponseEntity.ok("Уведомление успешно отправлено вручную");
    }
}
