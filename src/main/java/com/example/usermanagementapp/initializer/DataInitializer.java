package com.example.usermanagementapp.initializer;

import com.example.usermanagementapp.service.UserGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final UserGeneratorService userGeneratorService;

    @Override
    public void run(ApplicationArguments args) {
        userGeneratorService.generateAndSaveUsers();
    }
}
