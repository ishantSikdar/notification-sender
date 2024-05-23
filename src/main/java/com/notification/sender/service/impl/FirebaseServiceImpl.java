package com.notification.sender.service.impl;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.notification.sender.dto.PushNotification;
import com.notification.sender.service.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class FirebaseServiceImpl implements FirebaseService {

    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public FirebaseServiceImpl(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @Override
    public Boolean sendPushNotification(PushNotification pushNotification)  {
        try {
            log.info("TOKEN {}", pushNotification.getToken());
            Message messageToSend = getPreconfiguredMessageToSend(pushNotification);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(messageToSend);
            String response = firebaseMessaging.send(messageToSend);
            log.info("Sent message to token. Device token: " + pushNotification.getToken() + ", " + response + " msg " + jsonOutput);
            return true;

        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message getPreconfiguredMessageToSend(PushNotification pushNotification) {
        return Message.builder()
                .setToken(pushNotification.getToken())
                .setNotification(Notification.builder()
                        .setTitle(pushNotification.getTitle())
                        .setBody(pushNotification.getBody())
                        .build())
                .putData("link", pushNotification.getClickAction())
                .setAndroidConfig(getAndroidConfig(pushNotification))
                .setApnsConfig(getApnsConfig(pushNotification))
                .setWebpushConfig(getWebpushConfig(pushNotification))
                .build();
    }

    // build android notification config
    private AndroidConfig getAndroidConfig(PushNotification pushNotificationDTO) {
        return AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setTitle(pushNotificationDTO.getTitle())
                        .setBody(pushNotificationDTO.getBody())
                        .setIcon(pushNotificationDTO.getIcon())
                        .build())
                .build();
    }

    // build iOS notification config
    private ApnsConfig getApnsConfig(PushNotification pushNotificationDTO) {
        Aps aps = Aps.builder()
                .setAlert(ApsAlert.builder()
                        .setTitle(pushNotificationDTO.getTitle())
                        .setBody(pushNotificationDTO.getBody())
                        .build())
                .setSound("default")
                .build();

        return ApnsConfig.builder()
                .setAps(aps)
                .build();
    }

    // build web app notification config
    private WebpushConfig getWebpushConfig(PushNotification pushNotificationDTO) {
        return WebpushConfig.builder()
                .setNotification(new WebpushNotification(pushNotificationDTO.getTitle(), pushNotificationDTO.getBody(), pushNotificationDTO.getIcon()))
                .setFcmOptions(WebpushFcmOptions.withLink(pushNotificationDTO.getClickAction()))
                .build();
    }
}
