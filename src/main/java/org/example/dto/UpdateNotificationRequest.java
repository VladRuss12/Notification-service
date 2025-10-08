package org.example.dto;

import lombok.Data;
import org.example.model.NotificationEnum;

import java.time.LocalDate;

@Data
public class UpdateNotificationRequest {
    private String title;
    private String description;
    private NotificationEnum type;
    private LocalDate eventDate;
    private boolean archived;
}
