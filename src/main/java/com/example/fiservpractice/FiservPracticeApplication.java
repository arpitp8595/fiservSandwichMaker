package com.example.fiservpractice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class FiservPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiservPracticeApplication.class, args);
    }

}
