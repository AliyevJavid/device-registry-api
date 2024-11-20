package com.example.deviceregistryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * Represents a device entity stored in the database.
 */
@Entity
@Getter
@Setter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @CreatedDate
    private LocalDateTime createdAt;

    public Device() {
    }

    public Device(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }
}
