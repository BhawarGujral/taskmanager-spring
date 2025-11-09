package com.bhawar.taskmanager.repository;

import com.bhawar.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository  extends JpaRepository<Task, Long> {

}
