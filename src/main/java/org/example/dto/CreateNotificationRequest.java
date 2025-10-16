package org.example.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.model.ENotificationType;

import java.time.LocalDate;

@Data
public class CreateNotificationRequest {

    @NotBlank(message = "Поле 'createdBy' не может быть пустым")
    @Size(max = 100, message = "Поле 'createdBy' не должно превышать 100 символов")
    private String createdBy;

    @NotNull(message = "Тип уведомления (type) обязателен")
    private ENotificationType type;

    @NotBlank(message = "Заголовок уведомления обязателен")
    @Size(max = 150, message = "Заголовок не должен превышать 150 символов")
    private String title;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    @NotNull(message = "Дата события обязательна")
    @FutureOrPresent(message = "Дата события не может быть в прошлом")
    private LocalDate eventDate;
}
