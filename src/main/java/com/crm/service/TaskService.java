package com.crm.service;

import com.crm.entity.Task;
import com.crm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> updateTaskStatus(Long id, String status) {
        Optional<Task> existing = taskRepository.findById(id);
        if (existing.isPresent()) {
            Task task = existing.get();
            task.setStatus(status);
            taskRepository.save(task);
            return Optional.of(task);
        }
        return Optional.empty();
    }
}
