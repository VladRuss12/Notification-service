package org.example.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.example.repository.NotificationSendLogRepository;
import org.example.service.sender.NotificationSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationJobService {

    private final NotificationRepository repository;
    private final NotificationSendLogRepository logRepository;
    private final NotificationSender sender;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

    @Scheduled(cron = "0 0 8 * * *")
    public void planTodayJobs() {
        LocalDate today = LocalDate.now();
        List<Notification> notifications = repository.findByArchived(false);

        for (Notification n : notifications) {
            long daysUntil = ChronoUnit.DAYS.between(today, n.getEventDate());

            if (daysUntil == 5 && !wasSent(n, "5_days")) {
                scheduleNotification(n, TimeUnit.MINUTES.toMillis(1), "5_days");
            } else if (daysUntil == 1 && !wasSent(n, "1_day")) {
                scheduleNotification(n, TimeUnit.MINUTES.toMillis(1), "1_day");
            } else if (daysUntil == 0 && !wasSent(n, "on_day")) {
                scheduleNotification(n, TimeUnit.MINUTES.toMillis(1), "on_day");
            }
        }
    }

    private boolean wasSent(Notification n, String stage) {
        return logRepository.findByNotificationIdAndStage(n.getId(), stage).isPresent();
    }

    private void scheduleNotification(Notification n, long delayMillis, String stage) {
        executor.schedule(() -> {
            try {
                sender.send(n, stage);
                log.info("Notification {} [{}] sent and logged", n.getId(), stage);
            } catch (Exception e) {
                log.error("Failed to send notification {} [{}]: {}", n.getId(), stage, e.getMessage());
            }
        }, delayMillis, TimeUnit.MILLISECONDS);
    }
}
