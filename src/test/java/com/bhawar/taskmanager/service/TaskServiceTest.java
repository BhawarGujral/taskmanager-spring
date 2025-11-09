package com.bhawar.taskmanager.service;

import com.bhawar.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

//Modern way of telling spring to initial mock class whenever we run this class
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    //We are creating mock of the repository not actually intializing it because
    // we dont want to touch real database data
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

    
}
