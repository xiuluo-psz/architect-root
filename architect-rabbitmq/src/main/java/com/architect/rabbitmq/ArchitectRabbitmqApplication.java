package com.architect.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ArchitectRabbitmqApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(ArchitectRabbitmqApplication.class, args);
    }
}
