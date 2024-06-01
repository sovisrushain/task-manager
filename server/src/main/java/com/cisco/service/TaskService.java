package com.cisco.service;

import com.cisco.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getAllTasks();
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(Integer id, TaskDTO taskDTO);
    Integer deleteTask(Integer id);
}
