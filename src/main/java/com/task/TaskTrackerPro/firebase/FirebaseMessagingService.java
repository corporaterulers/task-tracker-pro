package com.task.TaskTrackerPro.firebase;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {

    public String sendNotification(String token, String title, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .putData("title", title)
                .putData("body", body)
                .setToken(token)
                .build();
        return FirebaseMessaging.getInstance().send(message);
    }
}
