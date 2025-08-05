package com.task.TaskTrackerPro.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/token")
    public String receiveToken(@RequestBody TokenRequest request) {
        System.out.println("Email: " + request.getEmail());
        System.out.println("Token: " + request.getToken());
        return "Token received successfully";
    }
    @PostMapping("/save-token")
    public ResponseEntity<String> saveToken(@RequestBody TokenRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFcmToken(request.getToken());
            userRepository.save(user);
            return ResponseEntity.ok("Token saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}

