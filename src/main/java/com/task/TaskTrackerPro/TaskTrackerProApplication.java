package com.task.TaskTrackerPro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TaskTrackerProApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerProApplication.class, args);
	}
}
