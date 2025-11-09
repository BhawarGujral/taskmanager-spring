package com.bhawar.taskmanager.repository;

import com.bhawar.taskmanager.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //Added specifically to test JPA repositories in spring boot.
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void testSaveTask(){
        //arrange
        Task task = new Task();
        task.setTitle("Test task");
        task.setStatus("To do");

        //act
        Task savedTask = taskRepository.save(task);

        //assert
        assertNotNull(savedTask);
        assertEquals(task.getTitle(),savedTask.getTitle());
    }
}
