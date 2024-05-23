package com.notification.sender.controller;

import com.notification.sender.dto.ApiResponse;
import com.notification.sender.dto.NotificationRequest;
import com.notification.sender.service.PushNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class SendNotificationController {

    private final PushNotificationService pushNotificationService;

    @Autowired
    public SendNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping(value = "/sendNotification")
    public ResponseEntity<ApiResponse<?>> sendNotification(
            @RequestBody NotificationRequest notificationRequest
    ) {
        ApiResponse<?> apiResponse = new ApiResponse<>();

        try {
            pushNotificationService.sendNotificationToDevice(notificationRequest.getFcmToken());
            apiResponse.setMessage("Notification sent to device: " + notificationRequest.getFcmToken());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            apiResponse.setMessage("Failed to send notification");
            log.error("Failed to send notification, {}", e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}