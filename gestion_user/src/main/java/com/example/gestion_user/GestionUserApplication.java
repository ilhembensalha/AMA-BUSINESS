package com.example.gestion_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.example.gestion_user", "com.example.security"})
public class GestionUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionUserApplication.class, args);
    }
}
