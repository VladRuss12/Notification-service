package org.example.dto;

import lombok.Data;
import org.example.model.ENotificationType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private String createdBy;
    private ENotificationType type;
    private String title;
    private String description;
    private LocalDate eventDate;
    private LocalDateTime createdAt;
    private boolean archived;
}