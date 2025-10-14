package org.example.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.model.Notification;
import org.example.model.ENotificationType;
import org.example.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private static final Logger logger = Logger.getLogger(NotificationScheduler.class.getName());

    private final NotificationRepository repository;

    @Scheduled(fixedRate = 60_000)
    public void checkNotifications() {
        LocalDate today = LocalDate.now();
        List<Notification> all = repository.findByArchived(false);

        for (Notification n : all) {
            LocalDate eventDate = n.getEventDate();

            if (n.getType() == ENotificationType.BIRTHDAY) {
                eventDate = LocalDate.of(today.getYear(), eventDate.getMonth(), eventDate.getDayOfMonth());
            }

            if (!n.isSent5Days() && today.equals(eventDate.minusDays(5))) {
                logger.info("[5 days before] " + n.getTitle());
                n.setSent5Days(true);
            }

            if (!n.isSent1Day() && today.equals(eventDate.minusDays(1))) {
                logger.info("[1 day before] " + n.getTitle());
                n.setSent1Day(true);
            }

            if (!n.isSentOnDay() && today.equals(eventDate)) {
                logger.info("[Event Day] " + n.getTitle());
                n.setSentOnDay(true);
            }

            repository.save(n);
        }
    }
}