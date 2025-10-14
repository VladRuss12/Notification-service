package org.example.dto;

import lombok.Data;
import org.example.model.ENotificationType;

import java.time.LocalDate;

@Data
public class CreateNotificationRequest {
    private String createdBy;
    private ENotificationType type;
    private String title;
    private String description;
    private LocalDate eventDate;
}