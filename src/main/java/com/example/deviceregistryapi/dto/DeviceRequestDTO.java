package com.example.deviceregistryapi.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * A request object for creating or updating a device.
 * Contains the fields required to define a device.
 */
public record DeviceRequestDTO(@NotBlank(message = "Device name is required")
                               String name,
                               @NotBlank(message = "Device brand is required")
                               String brand) {
}
