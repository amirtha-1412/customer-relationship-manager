package com.crm.config;

import com.crm.entity.User;
import com.crm.repository.UserRepository;
import com.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public DataInitializer(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed admin user
        Optional<User> existingAdmin = userRepository.findByUsername("admin");
        if (existingAdmin.isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123");
            adminUser.setEmail("admin@crm.com");
            adminUser.setRole("ROLE_ADMIN");
            adminUser.setEnabled(true);
            userService.registerUser(adminUser);
            System.out.println("Default admin user created. (username: admin, role: ROLE_ADMIN)");
        } else {
            System.out.println("Admin user already exists. Skipping initialization.");
        }

        // Seed sales user
        Optional<User> existingSales = userRepository.findByUsername("sales");
        if (existingSales.isEmpty()) {
            User salesUser = new User();
            salesUser.setUsername("sales");
            salesUser.setPassword("sales123");
            salesUser.setEmail("sales@crm.com");
            salesUser.setRole("ROLE_SALES");
            salesUser.setEnabled(true);
            userService.registerUser(salesUser);
            System.out.println("Default sales user created. (username: sales, role: ROLE_SALES)");
        } else {
            System.out.println("Sales user already exists. Skipping initialization.");
        }
    }
}

