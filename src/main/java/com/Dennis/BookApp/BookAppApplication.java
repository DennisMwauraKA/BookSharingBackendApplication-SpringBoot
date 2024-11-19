package com.Dennis.BookApp;

import com.Dennis.BookApp.repo.RoleRepo;
import com.Dennis.BookApp.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class BookAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookAppApplication.class, args);
    }


    @Bean
    public CommandLineRunner runner(RoleRepo roleRepo) {
        return args -> {
            if (roleRepo.findByName("USER").isEmpty()) {
                Role userRole = new Role();
                userRole.setName("USER");
                roleRepo.save(userRole);
            }
        };

    }

}
