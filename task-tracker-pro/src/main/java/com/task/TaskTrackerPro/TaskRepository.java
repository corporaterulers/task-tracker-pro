package com.task.TaskTrackerPro;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task , Long> { // for connecting to repository without any expections
}
