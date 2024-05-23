package com.notification.sender.service;

import java.util.concurrent.ExecutionException;

public interface PushNotificationService {
    void sendNotificationToDevice(String deviceId) throws ExecutionException, InterruptedException;
}
