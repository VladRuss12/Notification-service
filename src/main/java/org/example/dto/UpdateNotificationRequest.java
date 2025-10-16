package org.example.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.model.ENotificationType;

import java.time.LocalDate;

@Data
public class UpdateNotificationRequest {

    @NotBlank(message = "Заголовок уведомления не может быть пустым")
    private String title;

    private String description;

    @NotNull(message = "Тип уведомления обязателен")
    private ENotificationType type;

    @NotNull(message = "Дата события обязательна")
    @FutureOrPresent(message = "Дата события не может быть в прошлом")
    private LocalDate eventDate;

    private boolean archived;
}
