package com.bhawar.taskmanager.service;

import com.bhawar.taskmanager.exception.TaskNotFoundException;
import com.bhawar.taskmanager.model.Task;
import com.bhawar.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Long id, String status){
        Task taskToUpdate = getTaskById(id);
        taskToUpdate.setStatus(status);
        return taskRepository.save(taskToUpdate);
    }

    public Task updateTask(Long id,Task updatedTaskData){
        Task taskToUpdate = getTaskById(id);
        taskToUpdate.setTitle(updatedTaskData.getTitle());
        taskToUpdate.setStatus(updatedTaskData.getStatus());
        return taskRepository.save(taskToUpdate);
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found , id: " + id));
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public void deleteTask(Long id){
        Task taskToDelete = getTaskById(id);
        taskRepository.delete(taskToDelete);
    }
}
