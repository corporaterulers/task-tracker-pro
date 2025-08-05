package com.task.TaskTrackerPro.notification;

import com.task.TaskTrackerPro.firebase.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;
    @PostMapping("/notify")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        try {
            firebaseMessagingService.sendNotification(request.getToken(), request.getTitle(), request.getBody());
            return ResponseEntity.ok("Notification sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send notification: " + e.getMessage());
        }
    }
}

