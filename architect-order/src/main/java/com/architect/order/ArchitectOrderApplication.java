package com.architect.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class ArchitectOrderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(ArchitectOrderApplication.class, args);
    }

}

