package org.example.service.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Notification;
import org.example.model.NotificationSendLog;
import org.example.repository.NotificationRepository;
import org.example.repository.NotificationSendLogRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpNotificationSenderService implements NotificationSender {

    private final NotificationRepository notificationRepository;
    private final NotificationSendLogRepository logRepository;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public void send(Notification notification, String stage) {
        try {
            String message = notification.getTitle() + "\nЭтап: " + stage;
            String baseUrl = "http://localhost:8081/api/receiveNotification";
            String url = baseUrl + "?title=" + URLEncoder.encode(notification.getTitle(), StandardCharsets.UTF_8)
                    + "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Notification sent: {}, response: {}", notification.getTitle(), response.body());

            NotificationSendLog logEntity = NotificationSendLog.builder()
                    .notification(notification)
                    .stage(stage)
                    .sentAt(LocalDateTime.now())
                    .build();

            logRepository.save(logEntity);

        } catch (Exception e) {
            log.error("Ошибка при отправке уведомления: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void sendDailyNotifications() {
        List<Notification> notifications = notificationRepository.findByArchived(false);

        if (notifications.isEmpty()) {
            log.info("Нет активных уведомлений для рассылки.");
            return;
        }

        log.info("Автоматическая отправка {} уведомлений...", notifications.size());
        for (Notification n : notifications) {
            send(n, "daily");
        }
    }
}
