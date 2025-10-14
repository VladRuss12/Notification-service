package org.example.dto;

import lombok.Data;
import org.example.model.ENotificationType;

import java.time.LocalDate;

@Data
public class UpdateNotificationRequest {
    private String title;
    private String description;
    private ENotificationType type;
    private LocalDate eventDate;
    private boolean archived;
}