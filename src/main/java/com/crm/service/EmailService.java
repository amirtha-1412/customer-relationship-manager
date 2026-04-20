package com.crm.service;

import com.crm.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.notification.to}")
    private String notificationTo;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTaskCreatedNotification(Task task) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(notificationTo);
            message.setSubject("New Task Assigned: " + task.getTitle());
            message.setText(buildTaskEmailBody(task));
            mailSender.send(message);
        } catch (Exception e) {
            // Log failure but don't break task creation flow
            System.err.println("[EmailService] Failed to send task notification: " + e.getMessage());
        }
    }

    private String buildTaskEmailBody(Task task) {
        return "A new task has been assigned in the CRM system.\n\n"
                + "-------------------------------\n"
                + "Task Title   : " + task.getTitle() + "\n"
                + "Description  : " + (task.getDescription() != null ? task.getDescription() : "N/A") + "\n"
                + "Due Date     : " + (task.getDueDate() != null ? task.getDueDate().toString() : "Not set") + "\n"
                + "Status       : " + task.getStatus() + "\n"
                + "Assigned To  : " + (task.getAssignedTo() != null ? task.getAssignedTo() : "Unassigned") + "\n"
                + "-------------------------------\n\n"
                + "Please log in to the CRM system to view full details.\n"
                + "http://localhost:8080/tasks";
    }
}
