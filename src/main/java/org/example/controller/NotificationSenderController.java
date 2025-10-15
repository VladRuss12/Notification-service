package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Notification;
import org.example.service.sender.HttpNotificationSenderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class NotificationSenderController {

    private final HttpNotificationSenderService senderService;

    @PostMapping
    public String sendNotification(@RequestBody Notification notification,
                                   @RequestParam(defaultValue = "manual") String stage) {
        senderService.send(notification, stage);
        return "✅ Notification sent manually";
    }
}
