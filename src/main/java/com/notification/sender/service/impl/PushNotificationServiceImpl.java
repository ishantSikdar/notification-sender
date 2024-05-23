package com.notification.sender.service.impl;

import com.notification.sender.dto.MemeDTO;
import com.notification.sender.dto.PushNotification;
import com.notification.sender.service.FirebaseService;
import com.notification.sender.service.MemeService;
import com.notification.sender.service.PushNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class PushNotificationServiceImpl implements PushNotificationService {

    private final FirebaseService firebaseService;
    private final MemeService memeService;

    @Autowired
    public PushNotificationServiceImpl(FirebaseService firebaseService, MemeService memeService) {
        this.firebaseService = firebaseService;
        this.memeService = memeService;
    }


    @Override
    public void sendNotificationToDevice(String deviceId) throws ExecutionException, InterruptedException {
        try {
            MemeDTO meme = memeService.getMeme();
            log.debug(meme.toString());
            firebaseService.sendPushNotification(
                    PushNotification.builder()
                            .token(deviceId)
                            .title(meme.getTitle())
                            .body("New Meme by: " + meme.getAuthor())
                            .icon(new ClassPathResource("static/pushIcon.png").getPath())       // icon of notification
                            .clickAction(meme.getUrl())  // public hyperlink where the user should be redirected to after clicking the push notification
                            .build()
            );
        } catch (Exception e) {
            log.error("Error sending meme notification: {}", e.getMessage());
            throw new RuntimeException("Error sending meme notification " + e.getMessage());
        }

    }

}
