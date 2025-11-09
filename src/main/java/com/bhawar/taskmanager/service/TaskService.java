package com.bhawar.taskmanager.service;

import com.bhawar.taskmanager.model.Task;
import com.bhawar.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task updateTaskStatus(Long id, String status){
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            Task t = task.get();
            t.setStatus(status);
            return taskRepository.save(t);
        }
        return null;
    }

}
