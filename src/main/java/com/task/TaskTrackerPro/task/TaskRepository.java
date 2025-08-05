package com.task.TaskTrackerPro.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // for connecting to repository and for handling the expections
}
