package com.task.TaskTrackerPro.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/firebase/notify")
public class FirebaseController {

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestParam String token,
                                                   @RequestParam String title,
                                                   @RequestParam String body) {
        try {
            String response = firebaseMessagingService.sendNotification(token, title, body);
            return ResponseEntity.ok("Notification sent with ID: " + response);
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send notification: " + e.getMessage());
        }
    }
}
