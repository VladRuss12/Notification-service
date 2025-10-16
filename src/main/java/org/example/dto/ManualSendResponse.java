package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualSendResponse {

    private Long notificationId;
    private String stage;
    private LocalDateTime sentAt;
    private String status;
}
