package com.task.TaskTrackerPro;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hilton/task")
public class TaskController {
    private final TaskRepository repository ;

    public TaskController(TaskRepository repository){
        this.repository = repository;
    }
    @GetMapping
    public List <Task> getTask(){
        return repository.findAll();
    }
    @PostMapping
    public Task createNewTask(@RequestBody Task task){
        return repository.save(task);
    }


}
