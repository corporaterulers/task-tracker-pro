package com.task.TaskTrackerPro.task;

import com.task.TaskTrackerPro.client.TokenRequest;
import com.task.TaskTrackerPro.client.User;
import com.task.TaskTrackerPro.client.UserRepository;
import com.task.TaskTrackerPro.notification.NotificationSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/hilton/task") // localhost:8080
public class TaskController {
    @Autowired
    private final TaskRepository taskRepository ;
    @Autowired
    private NotificationSchedulerService notificationSchedulerService;
    private UserRepository userRepository;
    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    @GetMapping("/")
    public String home() {
        return "Welcome to Task Tracker Pro API";
    }
    @GetMapping // REST endpoints Get
    public List <Task> getTask(){
        return taskRepository.findAll();
    }
    @PostMapping // REST endpoints post
    public Task createNewTask(@RequestBody Task task){
        return taskRepository.save(task);
    }
    @PostMapping("/token")
    public String receiveToken(@RequestBody TokenRequest request) {
        // Log or process the token
        System.out.println("Received token: " + request.getToken() + " for email: " + request.getEmail());
        return "Token received successfully";
    }
    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Optional<User> optionalUser = userRepository.findByEmail(task.getUser().getEmail());
        if (optionalUser.isPresent()) {
            task.setUser(optionalUser.get());
            taskRepository.save(task);
            notificationSchedulerService.addToQueue(task);
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
