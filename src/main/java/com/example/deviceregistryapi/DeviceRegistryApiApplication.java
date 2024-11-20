package com.example.deviceregistryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DeviceRegistryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceRegistryApiApplication.class, args);
    }

}
