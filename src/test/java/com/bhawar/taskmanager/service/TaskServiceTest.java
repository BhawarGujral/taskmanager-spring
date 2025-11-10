package com.bhawar.taskmanager.service;

import com.bhawar.taskmanager.exception.TaskNotFoundException;
import com.bhawar.taskmanager.model.Task;
import com.bhawar.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
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
    void testGetTaskById(){
        //arrange
        Task task = new Task(1L,"Task 1","To Do");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //act
        Task retreivedTask = taskService.getTaskById(1L);

        //assert
        assertNotNull(retreivedTask);
        assertEquals(1L, retreivedTask.getId());
        assertEquals("Task 1",retreivedTask.getTitle());
        assertEquals("To Do",retreivedTask.getStatus());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskById_TaskNotFound(){
        //arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        //act && assert
        assertThrows(TaskNotFoundException.class,() ->taskService.getTaskById(1L));
        verify(taskRepository,times(1)).findById(1L);
    }

    @Test
    void testUpdateTaskStatus(){
        //aarange
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

    @Test
    void getAllTasks(){
        //arrange
        List<Task> tasks = Arrays.asList(
                new Task("Task1","To Do"),
                new Task("Task2","In Progress")
        );

        when(taskRepository.findAll()).thenReturn(tasks);

        //act
        List<Task> retrivedTasks = taskService.getAllTasks();

        //assert
        assertNotNull(retrivedTasks);
        assertEquals(2, retrivedTasks.size());
        verify(taskRepository,times(1)).findAll();
    }

    @Test
    void testCreateTask(){
        //arrange
        Task task = new Task("Test Task","To Do");
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        //act
        Task createdTask = taskService.createTask(task);

        //assert
        assertNotNull(createdTask);
        assertEquals("Test Task",createdTask.getTitle());
        assertEquals("To Do",createdTask.getStatus());
        verify(taskRepository,times(1)).save(task);
    }

    @Test
    void testUpdateTask(){
        //arrange
        Task existingTask = new Task(1L,"Old Task","to do");
        Task updatedTaskData = new Task("New Title","Done");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //act
        Task updatedTask = taskService.updateTask(1L, updatedTaskData);

        //assert
        assertNotNull(updatedTask);
        assertEquals("New Title",updatedTask.getTitle());
        assertEquals("Done",updatedTask.getStatus());
        verify(taskRepository).save(existingTask);
    }

    @Test
    void testDeleteTask(){
        //arrange
        Task existingTask =  new Task(1L,"Test Task to delete","To Do");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        //act
        taskService.deleteTask(1L);

        //assert
        verify(taskRepository,times(1)).delete(existingTask);
        verify(taskRepository,times(1)).findById(1L);
    }

    @Test
    void testDeleteTask_TaskNotFound(){
        //arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        //act && assert
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).findById(1L);
    }
}
