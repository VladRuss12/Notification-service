package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.ManualSendResponse;
import org.example.logging.dto.NotificationLogResponse;
import org.example.dto.SendNotificationRequest;
import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.example.service.NotificationService;
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
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ManualSendResponse> sendNotification(@Valid @RequestBody SendNotificationRequest request) {
        Notification notification = repository.findById(request.getNotificationId())
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        // Используем метод сервиса для проверки дублирования и отправки
        NotificationLogResponse logDto = notificationService.sendNotificationIfNotSent(notification, request.getStage());

        // Формируем структурированный ответ
        ManualSendResponse response = ManualSendResponse.builder()
                .notificationId(logDto.getNotificationId())
                .stage(logDto.getStage())
                .sentAt(logDto.getSentAt())
                .status("SENT")
                .build();

        return ResponseEntity.ok(response);
    }

}
