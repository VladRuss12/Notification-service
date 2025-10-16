package org.example.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.ENotificationType;
import org.example.model.Notification;
import org.example.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationJobService {

    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 8 * * *")
    public void planTodayJobs() {
        LocalDate today = LocalDate.now();
        List<Notification> notifications = notificationService.getActiveNotifications();

        for (Notification n : notifications) {
            long daysUntil = calculateDaysUntil(n, today);

            try {
                if (daysUntil == 5) {
                    notificationService.sendNotificationIfNotSent(n, "5_days");
                } else if (daysUntil == 1) {
                    notificationService.sendNotificationIfNotSent(n, "1_day");
                } else if (daysUntil == 0) {
                    notificationService.sendNotificationIfNotSent(n, "on_day");
                }
            } catch (Exception e) {
                log.info("Notification {} already sent for stage {}", n.getId(), daysUntil);
            }
        }
    }

    private long calculateDaysUntil(Notification n, LocalDate today) {
        if (n.getType() == ENotificationType.BIRTHDAY) {
            LocalDate birthdayThisYear = n.getEventDate().withYear(today.getYear());
            if (birthdayThisYear.isBefore(today)) {
                birthdayThisYear = birthdayThisYear.plusYears(1);
            }
            return ChronoUnit.DAYS.between(today, birthdayThisYear);
        } else {
            return ChronoUnit.DAYS.between(today, n.getEventDate());
        }
    }
}
