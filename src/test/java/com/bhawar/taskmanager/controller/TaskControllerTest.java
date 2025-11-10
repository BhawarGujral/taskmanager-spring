package com.bhawar.taskmanager.controller;

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
}
