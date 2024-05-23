package com.notification.sender.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PushNotification {
    private String token;
    private String title;
    private String body;
    private String icon;
    private String clickAction;
}
