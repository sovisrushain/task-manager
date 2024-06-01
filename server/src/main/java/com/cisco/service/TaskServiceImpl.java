package com.cisco.service;

import com.cisco.dto.TaskDTO;
import com.cisco.model.Task;
import com.cisco.model.UserEntity;
import com.cisco.repository.TaskRepository;
import com.cisco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public List<TaskDTO> getAllTasks() {
        UserEntity user = getCurrentUser();
        List<Task> taskList = taskRepository.findTasksById(user.getId());
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : taskList) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTitle(task.getTitle());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setCompleted(task.getCompleted());
            taskDTOList.add(taskDTO);
        }
        return taskDTOList;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        UserEntity user = getCurrentUser();
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        task.setUser(user);
        taskRepository.save(task);
        return taskDTO;
    }

    @Override
    public TaskDTO updateTask(Integer id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        if (!(task.getUser().getId() == getCurrentUser().getId())) {
            throw new RuntimeException("You are not authorized to update this todo");
        }
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        taskRepository.save(task);
        return taskDTO;
    }

    @Override
    public Integer deleteTask(Integer id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        if (!(task.getUser().getId() == getCurrentUser().getId())) {
            throw new RuntimeException("You are not authorized to delete this todo");
        }
        taskRepository.delete(task);
        return task.getId();
    }

    private UserEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

