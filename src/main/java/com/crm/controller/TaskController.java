package com.crm.controller;

import com.crm.entity.Task;
import com.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /tasks - Display all tasks
    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("pageTitle", "Tasks");
        return "tasks/list";
    }

    // GET /tasks/new - Show form to create a new task
    @GetMapping("/new")
    public String showNewTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("pageTitle", "Add Task");
        return "tasks/form";
    }

    // POST /tasks - Save new task to database
    @PostMapping
    public String saveTask(@ModelAttribute("task") Task task) {
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    // GET /tasks/edit/{id} - Show edit form for existing task
    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            model.addAttribute("pageTitle", "Edit Task");
            return "tasks/form";
        }
        return "redirect:/tasks";
    }

    // POST /tasks/edit/{id} - Update existing task
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id,
                             @ModelAttribute("task") Task task) {
        task.setId(id);
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    // GET /tasks/delete/{id} - Delete task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
