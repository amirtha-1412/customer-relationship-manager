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
        // Check if admin user already exists to avoid DuplicateKeyException
        Optional<User> existingAdmin = userRepository.findByUsername("admin");
        
        if (existingAdmin.isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            // The password will be hashed via BCryptPasswordEncoder inside the UserService
            adminUser.setPassword("admin123"); 
            adminUser.setEmail("admin@crm.com");
            adminUser.setRole("ROLE_ADMIN");
            adminUser.setEnabled(true);
            
            userService.registerUser(adminUser);
            System.out.println("Default admin user created successfully! (username: admin, role: ROLE_ADMIN)");
        } else {
            System.out.println("Admin user already exists in the database. Skipping initialization.");
        }
    }
}
