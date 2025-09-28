package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;


@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createdBy;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String title;
    private String description;

    private LocalDate eventDate;

    private boolean sent5Days;
    private boolean sent1Day;
    private boolean sentOnDay;

    private LocalDateTime createdAt;

    // getters/setters
}

