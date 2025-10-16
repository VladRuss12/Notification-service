package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationSendLogResponse {
    private Long id;
    private Long notificationId;
    private LocalDateTime sentAt;
    private String stage;
}
