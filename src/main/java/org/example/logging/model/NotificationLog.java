package org.example.logging.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.Notification;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notification_send_log")
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Notification notification;

    private LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    private ENotificationStage stage; // "5_days", "1_day", "on_day"
}
