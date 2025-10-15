package org.example.service.sender;

import org.example.model.Notification;

public interface NotificationSender {
    void send(Notification notification, String stage);
}
