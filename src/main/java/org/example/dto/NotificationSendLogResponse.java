package org.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationSendLogResponse {
    private Long id;
    private Long notificationId;
    private LocalDateTime sentAt;
    private String stage;
}

