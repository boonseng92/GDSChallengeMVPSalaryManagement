package com.gds.swe.challenge;


import javax.annotation.Resource;

import com.gds.swe.challenge.Service.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application implements CommandLineRunner {
    @Resource
    FileStorageService fileStorageService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        fileStorageService.getAllEmployeeInfo();
    }
}
