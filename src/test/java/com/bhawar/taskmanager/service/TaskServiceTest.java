package com.bhawar.taskmanager.service;

import com.bhawar.taskmanager.model.Task;
import com.bhawar.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Modern way of telling spring to initial mock class whenever we run this class
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    //We are creating mock of the repository not actually intializing it because
    // we dont want to touch real database data and database interactions are costly to run
    @Mock
    private TaskRepository taskRepository;

    //Injecting the mock class in service to make it usable
    @InjectMocks
    private TaskService taskService;

    //Conventional way of initializing the mocks
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testUpdateTaskStatus(){
        //aarange
        //ID will not be auto generated because its a mock
        Task task = new Task(1L,"Existing Task","to do");

        // Find by id definition for dummy
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        //Save definition for dummy
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        //act
        Task updatedTask = taskService.updateTaskStatus(1L,"In Progress");

        //assert
        assertNotNull(updatedTask);
        assertEquals("In Progress", updatedTask.getStatus());
        //To make sure dummy is being called
        verify(taskRepository,times(1)).findById(1L);
        verify(taskRepository,times(1)).save(task);
    }

    

}
