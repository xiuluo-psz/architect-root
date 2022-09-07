package com.architect.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ArchitectKafkaApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(ArchitectKafkaApplication.class, args);
    }
}