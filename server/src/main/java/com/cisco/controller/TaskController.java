package com.cisco.controller;

import com.cisco.dto.TaskDTO;
import com.cisco.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private static final Logger logger = LogManager.getLogger(TaskController.class);

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        logger.info("TaskController::getAllTasks::started");
        List<TaskDTO> response = taskService.getAllTasks();
        logger.info("TaskController::getAllTasks::finish");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        logger.info("TaskController::createTask::started");
        TaskDTO response = taskService.createTask(taskDTO);
        logger.info("TaskController::createTask::finish");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Integer id, @RequestBody TaskDTO taskDTO) {
        logger.info("TaskController::updateTask::started");
        TaskDTO response = taskService.updateTask(id, taskDTO);
        logger.info("TaskController::updateTask::finish");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteTask(@PathVariable Integer id) {
        logger.info("TaskController::deleteTask::started");
        Integer res = taskService.deleteTask(id);
        logger.info("TaskController::deleteTask::finish");
        return ResponseEntity.ok(res);
    }
}
