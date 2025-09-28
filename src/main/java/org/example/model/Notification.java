package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public boolean isSent5Days() {
        return sent5Days;
    }

    public void setSent5Days(boolean sent5Days) {
        this.sent5Days = sent5Days;
    }

    public boolean isSent1Day() {
        return sent1Day;
    }

    public void setSent1Day(boolean sent1Day) {
        this.sent1Day = sent1Day;
    }

    public boolean isSentOnDay() {
        return sentOnDay;
    }

    public void setSentOnDay(boolean sentOnDay) {
        this.sentOnDay = sentOnDay;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
