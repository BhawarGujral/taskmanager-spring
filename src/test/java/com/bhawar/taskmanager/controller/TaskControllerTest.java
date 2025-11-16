package com.bhawar.taskmanager.controller;

import com.bhawar.taskmanager.exception.TaskNotFoundException;
import com.bhawar.taskmanager.model.Task;
import com.bhawar.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//WebMvc Annotation helps to create test cases using MockMvc
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    //It will create a mock service layer and we will tell it what to return for each function call
    @MockitoBean
    private TaskService taskService;

    //It is used to test HTTP Requests
    @Autowired
    private MockMvc mockMvc;

    //Object mapper helps to convert java object into JSON and other way around
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTask() throws Exception {
        //arrange
        Task task = new Task("Controller test task","To do");
        when(taskService.createTask(task)).thenReturn(task);

        //act && assert
        mockMvc.perform(post("/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Controller test task"));

    }

    @Test
    void testCreateTask_InvalidInput() throws Exception {
        //arrange
        Task task = new Task("","To do");

        //act && assert
        mockMvc.perform(post("/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetTaskById() throws Exception {
        //arrange
        Task task = new Task(1L,"Task 1","To do");
        when(taskService.getTaskById(1L)).thenReturn(task);

        //act && assert
        mockMvc.perform(get("/tasks/1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.status").value("To do"));
    }

    @Test
    void getTaskById_TaskNotFound() throws Exception {
        //assert
        when(taskService.getTaskById(1L)).thenThrow(new TaskNotFoundException("Task not found"));

        //act && assert
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound());

        verify(taskService).getTaskById(1L);
    }

    @Test
    void testGetAllTasks() throws Exception {
        //arrange
        List<Task>  tasks = Arrays.asList(
                new Task("Task 1","To Do"),
                new Task("Task 2","In Progress")
        );
        when(taskService.getAllTasks()).thenReturn(tasks);

        //act && assert
        mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));


    }

    @Test
    void testUpdateTask() throws Exception {
        //assert
        Task task = new Task(1L,"Updated Task","In progress");
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(task);

        String taskJson = objectMapper.writeValueAsString(task);

        //act && assert
        mockMvc.perform(put("/tasks/1")
                    .contentType((MediaType.APPLICATION_JSON))
                    .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Task"));

        verify(taskService).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void testUpdateTask_TaskNotFound() throws Exception {
        //arrange
        Task task = new Task(1L,"Updated Task","In progress");
        when(taskService.updateTask(eq(1L), any(Task.class))).thenThrow(new TaskNotFoundException("Task not found"));

        String taskJson = objectMapper.writeValueAsString(task);

        //act and assert
        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isNotFound());

        verify(taskService).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void testDeleteTask() throws Exception {
        //arrange
        doNothing().when(taskService).deleteTask(eq(1L));

        //act && assert
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(1L);
    }
}
