package com.cisco.service;

import com.cisco.dto.TaskDTO;
import com.cisco.model.Task;
import com.cisco.model.UserEntity;
import com.cisco.repository.TaskRepository;
import com.cisco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

    @Override
    public List<TaskDTO> getAllTasks() {
        logger.info("TaskServiceImpl::getAllTasks::started");
        UserEntity user = getCurrentUser();
        List<Task> taskList = taskRepository.findByUserId(user.getId());
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task : taskList) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setId(task.getId());
            taskDTO.setTitle(task.getTitle());
            taskDTO.setDescription(task.getDescription());
            taskDTO.setCompleted(task.getCompleted());
            taskDTOList.add(taskDTO);
        }
        logger.info("TaskServiceImpl::getAllTasks::done");
        return taskDTOList;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        logger.info("TaskServiceImpl::createTask::started");
        UserEntity user = getCurrentUser();
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        task.setUser(user);
        Task res = taskRepository.save(task);
        taskDTO.setId(res.getId());
        logger.info("TaskServiceImpl::createTask::done");
        return taskDTO;
    }

    @Override
    public TaskDTO updateTask(Integer id, TaskDTO taskDTO) {
        logger.info("TaskServiceImpl::updateTask::started");
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        if (!(task.getUser().getId() == getCurrentUser().getId())) {
            logger.error("TaskServiceImpl::updateTask::user not logged in");
            throw new RuntimeException("You are not authorized to update this todo");
        }
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        Task res = taskRepository.save(task);
        taskDTO.setId(res.getId());
        logger.info("TaskServiceImpl::updateTask::done");
        return taskDTO;
    }

    @Override
    public Integer deleteTask(Integer id) {
        logger.info("TaskServiceImpl::deleteTask::started");
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
        if (!(task.getUser().getId() == getCurrentUser().getId())) {
            logger.error("TaskServiceImpl::deleteTask::You are not authorized to delete this todo");
            throw new RuntimeException("You are not authorized to delete this todo");
        }
        taskRepository.delete(task);
        logger.info("TaskServiceImpl::deleteTask::done");
        return task.getId();
    }

    private UserEntity getCurrentUser() {
        logger.info("TaskServiceImpl::getCurrentUser::started");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        logger.info("TaskServiceImpl::getCurrentUser::done");
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

