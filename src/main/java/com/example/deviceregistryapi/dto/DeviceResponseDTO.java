package com.example.deviceregistryapi.dto;

import java.time.LocalDateTime;
/**
 * A response object for sending device data to the client.
 * Contains all fields that define a device.
 */
public record DeviceResponseDTO(Long id,
                                String name,
                                String brand,
                                LocalDateTime createdAt) {
}
