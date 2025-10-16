package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.ENotificationType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;
    private String createdBy;
    private ENotificationType type;
    private String title;
    private String description;
    private LocalDate eventDate;
    private LocalDateTime createdAt;
    private boolean archived;
    private List<SendLogInfo> sendLogs;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendLogInfo {
        private LocalDateTime sentAt;
        private String stage;
    }
}
