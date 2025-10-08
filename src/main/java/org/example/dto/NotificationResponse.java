package org.example.dto;

import lombok.Data;
import org.example.model.NotificationEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private String createdBy;
    private NotificationEnum type;
    private String title;
    private String description;
    private LocalDate eventDate;
    private boolean sent5Days;
    private boolean sent1Day;
    private boolean sentOnDay;
    private LocalDateTime createdAt;
    private boolean archived;
}
