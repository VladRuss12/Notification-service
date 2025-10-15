package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String createdBy;

    @Enumerated(EnumType.STRING)
    private ENotificationType type;

    private String title;
    private String description;

    private LocalDate eventDate;


    private LocalDateTime createdAt;

    private boolean archived;
}
