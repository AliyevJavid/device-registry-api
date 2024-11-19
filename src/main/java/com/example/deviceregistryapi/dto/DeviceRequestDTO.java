package com.example.deviceregistryapi.dto;

import jakarta.validation.constraints.NotBlank;

public record DeviceRequestDTO(@NotBlank(message = "Device name is required")
                               String name,
                               @NotBlank(message = "Device brand is required")
                               String brand) {
}
