package com.example.deviceregistryapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String brand;
    private LocalDateTime creationTime;

    public Device() {
    }

    public Device(String name, String brand, LocalDateTime creationTime) {
        this.name = name;
        this.brand = brand;
        this.creationTime = creationTime;
    }
}
