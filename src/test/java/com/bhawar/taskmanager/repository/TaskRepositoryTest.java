package com.bhawar.taskmanager.repository;

import com.bhawar.taskmanager.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //Added specifically to test JPA repositories in spring boot.
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void testSaveTask(){
        //arrange
        Task task = new Task("Test task","To do");

        //act
        Task savedTask = taskRepository.save(task);

        //assert
        assertNotNull(savedTask);
        assertEquals(task.getTitle(),savedTask.getTitle());
    }

    @Test
    void testFindAllTasks(){
        //arrange
        Task task1 = new Task("Task1","To do");
        Task task2 = new Task("Task2","To do");
        taskRepository.save(task1);
        taskRepository.save(task2);

        //act
        List<Task> tasks = taskRepository.findAll();

        //assert
        assertEquals(2, tasks.size());
    }

    @Test
    void testFindTaskById(){
        //arrange
        Task task = new Task("Task1","To do");
        taskRepository.save(task);

        //act
        Optional<Task> optionalTask = taskRepository.findById(task.getId());

        //assert
        assertFalse(optionalTask.isEmpty());
        assertEquals(task.getId(),optionalTask.get().getId());
    }

    @Test
    void testUpdateTaskStatus(){
        //arrange
        Task task = new Task("Task1","To do");
        taskRepository.save(task);

        //act
        task.setStatus("Done");
        taskRepository.save(task);
        Optional<Task> optionalTask = taskRepository.findById(task.getId());

        //assert
        assertFalse(optionalTask.isEmpty());
        assertEquals(task.getStatus(),optionalTask.get().getStatus());
    }

    @Test
    void testDeleteTask(){
        //arrange
        Task task = new Task("Task to delete","Done");
        taskRepository.save(task);

        //act
        taskRepository.delete(task);
        Optional<Task> deletedTask = taskRepository.findById(task.getId());

        //assert
        assertFalse(deletedTask.isPresent());
    }
}
