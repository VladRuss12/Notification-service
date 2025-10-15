package org.example.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.ENotificationType;
import org.example.model.Notification;
import org.example.model.NotificationSendLog;
import org.example.repository.NotificationRepository;
import org.example.repository.NotificationSendLogRepository;
import org.example.service.sender.NotificationSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationJobService {

    private final NotificationRepository repository;
    private final NotificationSendLogRepository logRepository;
    private final NotificationSender sender;

    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

    {
        taskScheduler.setPoolSize(5);
        taskScheduler.initialize();
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void planTodayJobs() {
        LocalDate today = LocalDate.now();
        List<Notification> notifications = repository.findByArchived(false);

        for (Notification n : notifications) {
            long daysUntil = calculateDaysUntil(n, today);

            if (daysUntil == 5 && !wasSent(n, "5_days")) {
                scheduleNotification(n, 60_000L, "5_days"); // 1 минута
            } else if (daysUntil == 1 && !wasSent(n, "1_day")) {
                scheduleNotification(n, 60_000L, "1_day");
            } else if (daysUntil == 0 && !wasSent(n, "on_day")) {
                scheduleNotification(n, 60_000L, "on_day");
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


    private boolean wasSent(Notification n, String stage) {
        return logRepository.findByNotificationIdAndStage(n.getId(), stage).isPresent();
    }


    private void scheduleNotification(Notification n, long delayMillis, String stage) {
        Instant runAt = Instant.now().plusMillis(delayMillis);
        taskScheduler.schedule(() -> sendAndLog(n, stage), runAt);
    }

    private void sendAndLog(Notification n, String stage) {
        try {
            sender.send(n, stage);

            NotificationSendLog sendLog = NotificationSendLog.builder()
                    .notification(n)
                    .stage(stage)
                    .sentAt(LocalDateTime.now())
                    .build();
            logRepository.save(sendLog);

            log.info("Notification {} [{}] sent and logged", n.getId(), stage);
        } catch (Exception e) {
            log.error("Failed to send notification {} [{}]: {}", n.getId(), stage, e.getMessage(), e);
        }
    }
}
