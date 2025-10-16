package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendNotificationRequest {

    @NotNull(message = "ID уведомления обязателен")
    private Long notificationId;

    @NotBlank(message = "Этап отправки обязателен (например: manual, daily)")
    private String stage;
}
