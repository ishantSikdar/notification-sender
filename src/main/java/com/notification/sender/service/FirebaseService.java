package com.notification.sender.service;

import com.notification.sender.dto.PushNotification;

import java.util.concurrent.ExecutionException;

public interface FirebaseService {

    Boolean sendPushNotification(PushNotification pushNotification) throws ExecutionException, InterruptedException;
}
