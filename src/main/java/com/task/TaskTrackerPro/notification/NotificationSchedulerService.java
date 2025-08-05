package com.task.TaskTrackerPro.notification;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.task.TaskTrackerPro.firebase.FirebaseMessagingService;
import com.task.TaskTrackerPro.task.Task;
import com.task.TaskTrackerPro.task.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class NotificationSchedulerService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;
    private PriorityQueue<Task> taskQueue;
    @PostConstruct
    public void init(){
        taskQueue = new PriorityQueue<>((a, b) -> a.getDueDate().compareTo(b.getDueDate()));
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            if (task.getDueDate() != null && task.getDueDate().isAfter(LocalDateTime.now())) {
                taskQueue.offer(task);
            }
        }
    }
    @Scheduled(fixedRate = 300000)
    public void checkDueTasks() throws FirebaseMessagingException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threshold = now.plusHours(1);
        List<Task> upcomingTasks = taskRepository.findAll().stream()
                .filter(task -> task.getDueDate() != null && task.getDueDate().isAfter(now) && task.getDueDate().isBefore(threshold))
                .toList();
        System.out.println("Scheduler ran at: " + now);
        System.out.println("Upcoming tasks found: " + upcomingTasks.size());
        for (Task task : upcomingTasks) {
            System.out.println("Sending notification for task: " + task.getTitle() + " due at: " + task.getDueDate());
            String title = "Task Reminder";
            String body = "Task '" + task.getTitle() + "' is due at " + task.getDueDate();
            String token = task.getUserToken();
            if (token == null || token.isEmpty() || !token.contains(":")) {
                System.out.println("Skipping notification invalid token for task: " + task.getTitle());
                continue;
            } else {
                firebaseMessagingService.sendNotification(title, body, token);
            }
        }
    }
    public void addToQueue(Task task) {
        if (task.getDueDate() != null && task.getDueDate().isAfter(LocalDateTime.now())) {
            taskQueue.offer(task);
        }
    }
}
